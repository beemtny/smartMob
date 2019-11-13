package com.example.smartmob;

import android.os.Looper;


import java.net.InetAddress;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;
import android.os.Handler;
import android.widget.Toast;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

interface OnWifiStateChangedListener{
    void onWifiStateChanged(ADHOC_STATUS state);
}

interface OnDataReceiveListener {
    void onDataReceive(Object data, InetAddress address);
}

public class ChatManager implements OnDataReceiveListener, OnWifiStateChangedListener {

//    private BloomFilter<String> mBloomfilter;
    private AdHocManager mAdHocManager;
    private String groupPin;

    private onAddNewMessageListener mOnAddNewMessageListener;

    ChatManager(String groupPin) {
        this.groupPin = groupPin;

        mAdHocManager = new AdHocManager();
        mAdHocManager.setupListener(this);
//        mAdHocManager.setOnWifiStateChangedListener(this);
//        mBloomfilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),1000,0.001);
//        setupBloomFilter();
    }

//    private void setupBloomFilter(){
//        List<ChatMessage> messages = DBManager.getInstance().fetchChatMessageList();
//        for(ChatMessage chatMessage:messages){
//            mBloomfilter.put(chatMessage.toBloomfilterString());
//        }
//    }

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
        DBManager.getInstance().addMessage(message);
//        mBloomfilter.put(message.toBloomfilterString());
        mAdHocManager.sendViaBroadcast(message);
    }

//    void sendBeacon(){
//        BeaconData beacon = new BeaconData(mBloomfilter);
//        mAdHocManager.sendViaBroadcast(beacon);
//    }

    @Override
    public void onWifiStateChanged(ADHOC_STATUS state) {
        if(state == ADHOC_STATUS.CONNECTED) {
            sendAllExisted();
        }
    }

    private boolean isNewMessage(ChatMessage chatMessage) {
        return !DBManager.getInstance().findMessage(chatMessage);
    }

    private void sendAllExisted(){
        List<ChatMessage> chatMessageList = DBManager.getInstance().fetchChatMessageList();
        for(ChatMessage m: chatMessageList){
            mAdHocManager.sendViaBroadcast(m);
        }
    }

    private void checkBloomFilter(BloomFilter<String> filter, InetAddress address){
        List<ChatMessage> messages = DBManager.getInstance().fetchChatMessageList();
        for(ChatMessage chatMessage:messages){
//            if(!filter.mightContain(chatMessage.toBloomfilterString())){
                mAdHocManager.sendViaBroadcast(chatMessage);
//            }
        }
    }


    @Override
    public void onDataReceive(Object data, InetAddress address) {
//        if (data instanceof BeaconData) {
//            BeaconData beacon = (BeaconData)data;
//            showToast("Rec Beacon from " + address.getHostAddress());
//            checkBloomFilter(beacon.getBloomfilter(), address);
//            //todo Check bloomfilter then send back beacon and data
//            if(!address.getHostAddress().equals(BroadcastManager.getIpAddress()) && !address.getHostAddress().equals("192.168.43.1")){
//                sendBeacon();
//                showToast("Sendback Beacon");
//            }
//        }
        if (data instanceof ChatMessage){
            showToast("Rec Message from " + address.getHostAddress());
            ChatMessage chatMessage = (ChatMessage)data;
            if(isNewMessage(chatMessage)){
                showToast("forward new message");
                sendMessage(chatMessage);

//                if(!address.getHostAddress().equals("192.168.43.1")){
//                }
                if(groupPin.equals(chatMessage.getPin())) {
                    if (!mOnAddNewMessageListener.equals(null)){
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