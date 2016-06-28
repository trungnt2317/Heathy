package com.example.darkwinter.heathy;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.betomaluje.miband.ActionCallback;
import com.betomaluje.miband.MiBand;
import com.betomaluje.miband.MiBandService;
import com.betomaluje.miband.bluetooth.NotificationConstants;
import com.betomaluje.miband.model.VibrationMode;

public class MainActivity extends AppCompatActivity {

    private MiBand miBand = MiBand.getInstance(MainActivity.this);

    private final String TAG = getClass().getSimpleName();

    private boolean isConnected = false;

    protected void connect() {
        if (!miBand.isConnected()) {
            miBand.connect(new ActionCallback() {
                @Override
                public void onSuccess(Object data) {
                    Log.d(TAG, "Connected with Mi Band!");
                    isConnected = true;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect();

        if (isConnected) {
            miBand.startVibration(VibrationMode.VIBRATION_WITH_LED);
        }
    }
}
