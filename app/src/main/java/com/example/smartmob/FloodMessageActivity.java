package com.example.smartmob;

//import android.content.Intent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

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

        TextView PinView = findViewById(R.id.pin);
        PinView.setText(groupPin);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(FloodMessageActivity.this);

//set title
        builder.setTitle(message.getTitle());
//set message
        builder.setMessage(message.getText());
//sest cancle
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatManager.stop();
    }
}