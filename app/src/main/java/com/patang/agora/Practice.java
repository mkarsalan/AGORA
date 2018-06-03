package com.patang.agora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Practice extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Practice.this, Login.class);
//                internt.putExtra("Hi Zainab :)", result);
                startActivity(intent);

                // Arsalan wrote this line

            }
        });

        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Practice.this, Heatmap.class);
//                internt.putExtra("Hotmap", result);
                startActivity(intent);

                // Arsalan wrote this line

            }
        });


        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "DATABASE", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Practice.this, Database.class);
//                internt.putExtra("Hotmap", result);
                startActivity(intent);

                // Arsalan wrote this line

            }
        });



    }

}