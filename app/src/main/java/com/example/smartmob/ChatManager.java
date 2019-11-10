package com.example.smartmob;

import android.os.Looper;


import java.net.InetAddress;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;
import android.os.Handler;
import android.widget.Toast;



interface OnDataReceiveListener {
    void onDataReceive(Object data, InetAddress address);
}

public class ChatManager implements OnDataReceiveListener {

    private AdHocManager mAdHocManager;
    private String groupPin;

    private onAddNewMessageListener mOnAddNewMessageListener;

    ChatManager(String groupPin) {
        this.groupPin = groupPin;

        mAdHocManager = new AdHocManager();
        mAdHocManager.setupListener(this);
    }


    void setOnAddNewMessageListener(onAddNewMessageListener listener){
        mOnAddNewMessageListener = listener;
    }

    void start(){
        mAdHocManager.startAdHoc();
    }

    void stop(){
        mAdHocManager.stopAdhoc();
    }

    void stopSwitchWifi(){
        mAdHocManager.stopSwitchWifi();
    }

    void stopSwitchWifi2(){
        mAdHocManager.stopSwitchWifi2();
    }

    void sendMessage(ChatMessage message){
        mAdHocManager.sendViaBroadcast(message);
        DBManager.getInstance().addMessage(message);
    }


    private boolean isNewMessage(ChatMessage chatMessage) {
        return !DBManager.getInstance().findMessage(chatMessage);
    }
    @Override
    public void onDataReceive(Object data, InetAddress address) {
        if(data instanceof ChatMessage){
            showToast("Rec Message from " + address.getHostAddress());
            ChatMessage chatMessage = (ChatMessage)data;
            if(isNewMessage(chatMessage)){
                DBManager.getInstance().addMessage(chatMessage);
                if(!address.getHostAddress().equals("192.168.43.1")){
                    mAdHocManager.sendViaBroadcast(chatMessage);
                }

                if(groupPin.equals(chatMessage.getPin())) {
                    if (mOnAddNewMessageListener.equals(null)){
                        mOnAddNewMessageListener.onAddNewMessageToUi(chatMessage);
                        showToast("insert new message");
                    }
                }
            } else {
                showToast("message exist");
            }
        }
    }

    private void showToast(final String text){
        Handler mainHandler = new Handler(Looper.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AppApplication.getInstance().getContext(),text,Toast.LENGTH_SHORT).show();
            } // This is your code
        };
        mainHandler.post(myRunnable);
    }

}