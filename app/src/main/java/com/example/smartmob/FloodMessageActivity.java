package com.example.smartmob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class FloodMessageActivity extends AppCompatActivity {

    public void onExitBtn(View v){
        Intent intent = new Intent(this, JoinGroupActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood_message);

        String PIN = getIntent().getStringExtra("PIN");
        TextView pinView = findViewById(R.id.pin);
        pinView.setText(PIN);

        final Button button = findViewById(R.id.exitGroup);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onExitBtn(v);
            }
        });

        Button btn = findViewById(R.id.showAlert);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FloodMessageActivity.this);

                //set title
                builder.setTitle("Title");
                //set message
                builder.setMessage("message");
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
        });
    }
}