package com.example.himanishmunjal.myapplication;

import android.app.Application;
import android.content.Intent;

/**
 * Created by himanishmunjal on 14/07/18.
 */

public class AppBase extends Application {
    public static boolean hardOnMode = false;
    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, KioskService.class));
    }
}
