package com.patang.agora;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User");
    EditText editTextEmail;
    EditText editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;

    ProgressBar progressbar;
    public String Email;
    SignInButton button;



    // Views



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.buttonRegister).setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();


        //progressDialog = new ProgressDialog(this);
        findViewById(R.id.textViewSignin).setOnClickListener(this);



        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!= null)
        {
            finish();
            Intent intent = new Intent(Login.this, MapActivity.class);
            startActivity(intent);

        }


    }



    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        Email=email;
        String password = editTextPassword.getText().toString().trim();


        //checking if email area is empty or not
        if (email.isEmpty())
        {
            editTextEmail.setError("Please enter Email Address.");
            editTextEmail.requestFocus();
            return;
        }

        //Checking if the email address is valid or not
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please enter a valid Email Address.");
            editTextEmail.requestFocus();
            return;
        }

        //checking if password area is empty or not
        if (password.isEmpty())
        {
            editTextPassword.setError("Please enter your Password.");
            editTextPassword.requestFocus();
            return;
        }


        //NOW REGISTERING THE USER
        progressbar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            User user = new User(Email,"simple");
                            //String userId= Email.replace("@","");
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // userId= userId.replace(".","");
                            userRef.child(userId).setValue(user);
                            Toast.makeText(getApplicationContext(), "Registered Successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MapActivity.class);
                            startActivity(intent);
                        } else {

                            //checking that the email is already registered
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(), "You are already registered.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.buttonRegister){
            registerUser();
        }
        else if (view.getId() == R.id.textViewSignin)
        {
            startActivity(new Intent(this, signin.class));
        }

    }

}


