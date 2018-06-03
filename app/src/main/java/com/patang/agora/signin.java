package com.patang.agora;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText editTextEmail;
    EditText editTextPassword;
    ProgressBar progressbar;

    SignInButton button;
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User");
    private FirebaseAuth firebaseAuth;
    private TextView mStatusTextView;
    private static final int RC_SIGN_IN = 101;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "MAIN_ACTIVITY";
    public String personEmail;

    //  FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.login).setOnClickListener(this);



        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser()!= null)
        {
            finish();
            Intent intent = new Intent(signin.this, MapActivity.class);
            startActivity(intent);


        }

        findViewById(R.id.googlebutton).setOnClickListener(this);


        button = (SignInButton) findViewById(R.id.googlebutton);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        //Toast.makeText(getApplicationContext(), "User has been successfully logged in.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //    Log.w(TAG, "Google sign in failed", e);
                // ...
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            }
        }
    }



    private void updateUI(FirebaseUser user) {
        if (user != null) {

            //Database
            personEmail = user.getEmail();
            Uri personPhoto = user.getPhotoUrl();

            User user1 = new User(personEmail,"simple",personPhoto);
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            userRef.child(userId).setValue(user1);


            Toast.makeText(getApplicationContext(), "Successfully logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signin.this, MapActivity.class);
            startActivity(intent);

        }
    }






    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        //  Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        final Task<AuthResult> authResultTask = mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            //Toast.makeText(getApplicationContext(), "LOl", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                           /* Toast.makeText(getApplicationContext(), "User has been successfully logged in.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signin.this, MapActivity.class);
                            startActivity(intent);
                            finish();*/
                        } else {
                            Toast.makeText(getApplicationContext(), "Could not log in.", Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                            //   Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }

    private void login()
    {
        String email = editTextEmail.getText().toString().trim();
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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                //User successfully logged in
                if(task.isSuccessful())
                {
                    /*//change it according to the next screen which will be displayed
                    Intent intent = new Intent (Login.this, newActivity.class);

                    //wont go back to the main screen if press back button
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/


                    /*Toast.makeText(getApplicationContext(), "User has been successfully logged in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signin.this, MapActivity.class);
                    startActivity(intent);*/

                    Toast.makeText(getApplicationContext(), "User has been successfully logged in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signin.this, Practice.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.login){
            login();
        }
        else if (view.getId() == R.id.googlebutton)
        {
            signIn();

        }
    }
}
