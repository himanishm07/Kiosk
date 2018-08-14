package com.example.himanishmunjal.myapplication;


import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Process;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Created by fabio on 30/01/2016.
 */
public class MyService extends Service {
    public int counter=0;
    Context context = null;
    public MyService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
        context = applicationContext;
    }
    public void onCreate() {
        Log.d("himanish", "start service");
    }
    public MyService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("himanish", "start command");
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("restartService");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 2000, 2000); //
    }

    /**
     * it sets the timer to print the counter every x seconds 
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                context = getApplicationContext();
                try {
                    ActivityManager am = ((ActivityManager) context
                            .getSystemService(context.ACTIVITY_SERVICE));
                    List<ActivityManager.RunningAppProcessInfo> processInfo = am.getRunningAppProcesses();
                    String mainProcessName = context.getPackageName();
                    int myPid = android.os.Process.myPid();
                    for (ActivityManager.RunningAppProcessInfo info : processInfo) {
                        Log.d("himanish pid", Integer.toString(info.pid));
                        if (info.pid != myPid) {
                            Log.d("himanish killing ", Integer.toString(info.pid));
                            android.os.Process.killProcess(info.pid);
                            android.os.Process.sendSignal(info.pid, android.os.Process.SIGNAL_KILL);
                            am.killBackgroundProcesses(info.processName);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}