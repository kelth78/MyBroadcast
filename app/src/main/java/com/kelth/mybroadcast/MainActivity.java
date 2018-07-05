package com.kelth.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();
    static final String BROADCAST_CUSTOM_USER_ACTION = "kelth.intent.action.CUSTOM_USER_ACTION";
    static final String BROADCAST_SYSTEM_AIRPLANE_MODE = "android.intent.action.AIRPLANE_MODE";

    // Listen to AIRPLANE MODE broadcast from Android system or applications
    IntentFilter mIntentFilter;
    BroadcastReceiver mBroadcastReceiver;

    Button mBtnSendBroadcast;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.etReceivedBroadcast);
        mBtnSendBroadcast = findViewById(R.id.btnSendBroadcast);

        // Init intent filter
        mIntentFilter = new IntentFilter(BROADCAST_SYSTEM_AIRPLANE_MODE);
        mIntentFilter.addAction(BROADCAST_CUSTOM_USER_ACTION);

        // Demo sending custom user action broadcast
        mBtnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast(new Intent(BROADCAST_CUSTOM_USER_ACTION));
            }
        });

        // Handle broadcast received...
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                mEditText.append("Received broadcast " + intent.getAction());

                // Airplane mode on/off
                if (intent.getAction().equals(BROADCAST_SYSTEM_AIRPLANE_MODE)) {
                    boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
                    mEditText.append(" " + (isAirplaneModeOn ? "ON" : "OFF"));
                }
                if (intent.getAction().equals(BROADCAST_CUSTOM_USER_ACTION)) {
                    // No extra data to show
                }
                // next line
                mEditText.append("\n");
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, mIntentFilter);

    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }
}
