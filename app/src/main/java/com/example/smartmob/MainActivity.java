package com.example.smartmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String randomPin(){
        Random rand = new Random();
        int randNum = rand.nextInt(100000);
        String pin = String.format("%06d", randNum);
        return pin;
    }

    protected void onJoinNewGroupBtn(){
        Intent intent = new Intent(this, JoinGroupActivity.class);
        startActivity(intent);
    }

    protected void onCreateGroupBtn(){
        Intent intent = new Intent(this, CreateGroupActivity.class);
        String pin = randomPin();
        intent.putExtra("pin",pin);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_and_create);

        final Button button = findViewById(R.id.joinNewGroupButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onJoinNewGroupBtn();
            }
        });

        final Button create_group_button = findViewById(R.id.createGroupButton);
        create_group_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCreateGroupBtn();
            }
        });
    }
}
