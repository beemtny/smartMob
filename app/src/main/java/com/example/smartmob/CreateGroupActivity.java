package com.example.smartmob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateGroupActivity extends AppCompatActivity {

//    private ChatManager mChatManager;
    EditText titleInput;
    EditText messageInput;

    protected void onSend(String title, String message){
//        mChatManager.send()
        titleInput.setText("");
        messageInput.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent intent = getIntent();
//        String pin = intent.getExtras().getString("pin");

        titleInput = findViewById(R.id.title);
        messageInput = findViewById(R.id.messageInput);

        final String titleText = titleInput.getText().toString();
        final String messageText = messageInput.getText().toString();

        final Button sendButton = findViewById(R.id.createGroupButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSend(titleText, messageText);
            }
        });

//        mChatManager = new ChatManager(pin);
    }
}
