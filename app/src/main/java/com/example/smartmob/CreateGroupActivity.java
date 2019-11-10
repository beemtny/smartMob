package com.example.smartmob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class CreateGroupActivity extends AppCompatActivity {

    private ChatManager mChatManager;

    EditText titleInput;
    EditText messageInput;
    TextView groupPin;

    protected void onSend(ChatMessage message){
//        mChatManager.sendMessage(message);
        titleInput.setText("");
        messageInput.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent intent = getIntent();
        final String pin = intent.getExtras().getString("pin");

        titleInput = findViewById(R.id.title);
        messageInput = findViewById(R.id.messageInput);
        groupPin = findViewById(R.id.groupPin);

        final String titleText = titleInput.getText().toString();
        final String messageText = messageInput.getText().toString();

        groupPin.setText(pin);

        mChatManager = new ChatManager(pin);
//        mChatManager.start();

        final Button sendButton = findViewById(R.id.createGroupButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChatMessage message = new ChatMessage(UUID.randomUUID().toString(), pin, titleText, messageText);
                onSend(message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatManager.stop();
    }
}
