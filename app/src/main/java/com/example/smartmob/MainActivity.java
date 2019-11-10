package com.example.smartmob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    protected void onJoinNewGroupBtn(){
        Intent intent = new Intent(this, JoinGroupActivity.class);
        startActivity(intent);
    }

    protected void onCreateGroupBtn(){
        Intent intent = new Intent(this, CreateGroupActivity.class);
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
