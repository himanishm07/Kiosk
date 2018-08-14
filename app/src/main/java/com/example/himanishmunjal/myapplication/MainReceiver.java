package com.example.himanishmunjal.myapplication;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by himanishmunjal on 10/07/18.
 */

public class MainReceiver extends BroadcastReceiver {
    Intent mServiceIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("restartService")) {
            mServiceIntent = new Intent(context, MyService.class);
            context.startService(mServiceIntent);
        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            mServiceIntent = new Intent(context, MainActivity.class);
            context.startActivity(mServiceIntent);
        }
    }
}
