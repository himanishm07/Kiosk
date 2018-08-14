package com.example.himanishmunjal.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    Intent mServiceIntent;
    Context ctx;
    EditText editText;
    Button button, button2;
    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        Log.d("himanish ", "mainactivity opened");
        setContentView(R.layout.activity_main);
        if (!isMyServiceRunning(MyService.class)) {
            Log.d("himanish ", "starting service");
            mServiceIntent = new Intent(getApplicationContext(), KioskService.class);
            startService(mServiceIntent);
        }
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(mCorkyListener);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(mCorkyListener2);
        editText.setText("force on mode "+String.valueOf(AppBase.hardOnMode));
    }
    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        public void onClick(View v) {
            AppBase.hardOnMode = !AppBase.hardOnMode;
            editText.setText("force on mode "+String.valueOf(AppBase.hardOnMode));
        }
    };
    private View.OnClickListener mCorkyListener2 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), DummyActivity.class);
            startActivity(i);
        }
    };
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onUserLeaveHint()
    {
        Log.d("onUserLeaveHint","Home button pressed");
        // super.onUserLeaveHint();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("himanish button ", Integer.toString(keyCode));
        return false;
//        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
//            System.out.println("KEYCODE_HOME");
//            return true;
//        }
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            System.out.println("KEYCODE_BACK");
//            return true;
//        }
//        if ((keyCode == KeyEvent.KEYCODE_MENU)) {
//            System.out.println("KEYCODE_MENU");
//            return true;
//        }
//        return false;
    }

    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
    }
}
