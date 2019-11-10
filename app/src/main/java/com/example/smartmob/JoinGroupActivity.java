package com.example.smartmob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class JoinGroupActivity extends AppCompatActivity {

    public void onSubmitBtn(View v){
        EditText mGroupPinView = findViewById(R.id.groupPinInput);
        String groupPin = mGroupPinView.getText().toString().trim();

        Intent intent = new Intent(this, FloodMessageActivity.class);
        intent.putExtra("groupPin",groupPin);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        final Button button = findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSubmitBtn(v);
            }
        });
    }
}
