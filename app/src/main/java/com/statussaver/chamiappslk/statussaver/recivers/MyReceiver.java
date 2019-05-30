package com.statussaver.chamiappslk.statussaver.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.statussaver.chamiappslk.statussaver.service.MediaListenerService;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("log_tag-bootttttt", "Action :: " + intent.getAction());
//        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
        //make an intent to your Service as follows
//            Intent serviceIntent = new Intent(context, MyAlarmService.class);
//            context.startService(serviceIntent);
        Toast.makeText(context, "My brodcast fired ", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(context, MediaListenerService.class);
        //context.startService(myIntent);
        ContextCompat.startForegroundService(context, myIntent);
//        }


    }
}