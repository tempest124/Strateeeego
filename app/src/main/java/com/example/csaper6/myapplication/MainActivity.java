package com.example.csaper6.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutDevice;
import com.peak.salut.SalutServiceData;

public class MainActivity extends AppCompatActivity implements SalutDataCallback {
    private static final String TAG = MainActivity.class.getSimpleName() ;
    private Salut network;
    public static final String T = "wwwww";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintwo);

        SalutDataReceiver dataReceiver = new SalutDataReceiver(MainActivity.this, MainActivity.this);
        SalutServiceData serviceData = new SalutServiceData("TicTacToe", 13254, "MultiTac");
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);
        wifiManager.setWifiEnabled(true);
        network = new Salut(dataReceiver, serviceData, new SalutCallback() {
            @Override
            public void call() {
                Log.e(TAG, "Sorry, but this device does not support WiFi Direct.");
            }
        });

        FragmentManager fm = getSupportFragmentManager();
       if(fm.getFragments() == null || fm.getFragments().isEmpty()) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, new HostOrJoinFragment(), "host or join")
                    .commit();
        }
    }
    @Override
    public void onDataReceived(Object o) {

    }

    protected void joinNetwork() {
        network.discoverNetworkServices(new SalutDeviceCallback() {
            @Override
            public void call(SalutDevice device) {
                Log.d(TAG, "A device has connected with the name " + device.deviceName);
                network.registerWithHost(device, new SalutCallback() {
                    @Override
                    public void call() {
                        Log.d(TAG, "We're now registered.");
                        Intent i = new Intent(MainActivity.this, MainActivity2.class);
                        i.putExtra(T, false);
                        startActivity(i);
                    }
                }, new SalutCallback() {
                    @Override
                    public void call() {
                        Log.d(TAG, "We failed to register.");
                    }
                });
            }
        }, false);

//        network.discoverWithTimeout(new SalutCallback() {
//            @Override
//            public void call() {
//                Log.d(TAG, "Look at all these devices! " + network.foundDevices.toString());
//            }
//        }, new SalutCallback() {
//            @Override
//            public void call() {
//                Log.d(TAG, "Bummer, we didn't find anyone. ");
//            }
//        }, 5000);
    }

    protected void startNetworkService() {
        network.startNetworkService(new SalutDeviceCallback() {
            @Override
            public void call(SalutDevice device) {
                Log.d(TAG, device.readableName + " has connected!");
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                i.putExtra("", true);
                startActivity(i);
            }
        });

    }
}