package com.example.smartmob;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

interface onAddNewMessageListener {
    void onAddNewMessageToUi(ChatMessage message);
}

public class FloodMessageActivity extends AppCompatActivity implements onAddNewMessageListener {
    private ChatManager mChatManager;

    public void onExitBtn(View v){
        Intent intent = new Intent(this, JoinGroupActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood_message);

        Intent intent = getIntent();
        String groupPin = intent.getExtras().getString("groupPin");
        mChatManager = new ChatManager(groupPin);
        mChatManager.setOnAddNewMessageListener(this);
        mChatManager.start();

        final Button button = findViewById(R.id.exitGroup);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onExitBtn(v);
            }
        });
    }

    @Override
    public void onAddNewMessageToUi(final ChatMessage message) {
        // display message
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatManager.stop();
    }
}