package com.example.darkwinter.heathy;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.betomaluje.miband.ActionCallback;
import com.betomaluje.miband.MiBand;
import com.betomaluje.miband.bluetooth.NotificationConstants;

public class MainActivity extends AppCompatActivity {

    private boolean isConnected = false;

    private final String TAG = getClass().getSimpleName();

    private MiBand miBand;

    private int BT_REQUEST_CODE = 1001;

    private void connectToMiBand() {
        if (!miBand.isConnected()) {
            miBand.connect(myConnectionCallback);
        }

        // btn_connect.setEnabled(false);

        // textView_status.setText("Connecting...");
    }

    private ActionCallback myConnectionCallback = new ActionCallback() {
        @Override
        public void onSuccess(Object data) {
            // Log.d(TAG, "Connected with Mi Band!");

            isConnected = true;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // startMiBand();

                }
            });
        }

        @Override
        public void onFail(int errorCode, String msg) {
            // Log.e(TAG, "Fail: " + msg);
            isConnected = false;

            if (errorCode == NotificationConstants.BLUETOOTH_OFF) {
                //turn on bluetooth
                //Log.d(TAG, "turn on Bluetooth");
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BT_REQUEST_CODE, null);
            } else {
                //Log.d(TAG, "not found");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //stopMiBand();
                    }
                });
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miBand = MiBand.getInstance(MainActivity.this);

        //connectToMiBand();
        if (!miBand.isConnected()) {
            miBand.connect(new ActionCallback() {
                @Override
                public void onSuccess(Object data) {
                    Log.d(TAG, "Connected with Mi Band!");
                    //show SnackBar/Toast or something
                }

                @Override
                public void onFail(int errorCode, String msg) {
                    Log.d(TAG, "Connection failed: " + msg);
                }
            });
        } else {
            miBand.disconnect();
        }

    }
}
