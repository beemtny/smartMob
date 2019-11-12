package com.example.smartmob;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.util.List;

class AdHocManager {

    private ApManager mAPManager;
    private BroadcastManager mBroadcastManager;
    private OnWifiStateChangedListener mOnWifiStateChangedListener;

    BroadcastReceiver wifiReceiver;
    BroadcastReceiver apReceiver;
    BroadcastReceiver connectivityReceiver;

    AdHocManager(){
        mAPManager = new ApManager();
        mBroadcastManager = new BroadcastManager();

        initWifiStateReceiver();
    }

    void setupListener(OnDataReceiveListener listener){
        mBroadcastManager.setListener(listener);
    }

    void setOnWifiStateChangedListener(OnWifiStateChangedListener listener){
        mOnWifiStateChangedListener = listener;
    }

    void startAdHoc(){
        mAPManager.startAutoSwitchWifi();
        /// todo
        mBroadcastManager.startServer();
    }

    void stopAdhoc(){
        mAPManager.stopAutoSwitchWifi();
        mBroadcastManager.stopServer();
    }

    void stopSwitchWifi(){
        mAPManager.stopAutoSwitchWifi();
        mAPManager.turnClient();
    }

    void stopSwitchWifi2(){
        mAPManager.stopAutoSwitchWifi();
        mAPManager.turnHotspot();
    }


    //todo
    void sendViaBroadcast(Object message){
        mBroadcastManager.sendObject(message);
    }

    WIFI_MODE getCurrentMode(){
        return mAPManager.getCurrentMode();
    }

    private void initWifiStateReceiver(){
        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getExtras().getInt(WifiManager.EXTRA_WIFI_STATE)){
                    case WifiManager.WIFI_STATE_ENABLING:
                        //                        responseReceivedListener.onWifiStatusChanged("Wifi Enabling...");
                        return;
                    case WifiManager.WIFI_STATE_ENABLED:
                        mAPManager.connectToAp(mAPManager.wifiConfig);
                        //                        responseReceivedListener.onWifiStatusChanged("Wifi Enabled");
                        return;
                    case WifiManager.WIFI_STATE_DISABLING:
                        //                        responseReceivedListener.onWifiStatusChanged("Wifi Disabling...");
                        return;
                    case WifiManager.WIFI_STATE_DISABLED:
                        //                        responseReceivedListener.onWifiStatusChanged("Wifi Disabled");
                        return;
                }
            }
        };

        apReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getExtras().getInt("wifi_state")){
                    case 11:
                        //                        responseReceivedListener.onWifiStatusChanged("Hotspot Disabled");
                        return;
                    case 13:
                        //                        responseReceivedListener.onWifiStatusChanged("Hotspot Enabled");
                        return;
                    case 14:
                        //                        responseReceivedListener.onWifiStatusChanged("Hotspot Failed");
                        return;
                }
            }
        };

        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean status = intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
                if(status){
                    //                    responseReceivedListener.onWifiStatusChanged("Connecting...");
                } else {
                    //                    responseReceivedListener.onWifiStatusChanged("Connected");
                    mOnWifiStateChangedListener.onWifiStateChanged(ADHOC_STATUS.CONNECTED);
                }
            }
        };

        AppApplication.getInstance().getContext().registerReceiver(wifiReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        AppApplication.getInstance().getContext().registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        AppApplication.getInstance().getContext().registerReceiver(apReceiver, new IntentFilter("android.net.wifi.WIFI_AP_STATE_CHANGED"));
    }
}
