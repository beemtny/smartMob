package com.example.smartmob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class CreateGroupActivity extends AppCompatActivity {

    protected void onSend(){
        //TODO: Connect to backend and Get PIN from Backend to show

        //TODO: Link to message activity
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        final Button register_button = findViewById(R.id.createGroupButton);
        register_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSend();
            }
        });
    }
}
