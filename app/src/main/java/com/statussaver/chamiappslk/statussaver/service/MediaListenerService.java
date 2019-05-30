package com.statussaver.chamiappslk.statussaver.service;



import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import com.statussaver.chamiappslk.statussaver.activities.MainActivity;

import android.statussaver.com.statussaver.R;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import java.io.File;

public class MediaListenerService extends Service {

    public static FileObserver observer;
    private MediaPlayer player;




    public MediaListenerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        onTaskRemoved(intent);
        //Toast.makeText(this, "My Service Started and trying to watch ", Toast.LENGTH_LONG).show();
        startWatching();
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartintentservirce = new Intent(getApplicationContext(),this.getClass());
        restartintentservirce.setPackage(getPackageName());
        startService(restartintentservirce);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //startWatching();
    }

    private void startWatching() {

        //The desired path to watch or monitor
        //E.g Camera folder
        final String pathToWatch = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/.Statuses/";
       // Toast.makeText(this, "My Service Started and trying to watch " + pathToWatch, Toast.LENGTH_LONG).show();
        Log.e("MediaListenerService", "Start watching"+ "]");
        observer = new FileObserver(pathToWatch, FileObserver.ALL_EVENTS) { // set up a file observer to watch this directory
            @Override
            public void onEvent(int event, final String file) {
                if (event==32 && !file.equals(".probe")){

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(),"event is grater than 0",Toast.LENGTH_LONG).show();
                            //addNotification();


                        }
                    });
                }
                if (event == FileObserver.CREATE || event == FileObserver.CLOSE_WRITE || event == FileObserver.MODIFY || event == FileObserver.MOVED_TO && !file.equals(".probe")) { // check that it's not equal to .probe because thats created every time camera is launched
                    Log.d("MediaListenerService", "File created [" + pathToWatch + file + "]");

                    Toast.makeText(getBaseContext(), "File created [" + pathToWatch + file + "]", Toast.LENGTH_LONG).show();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), file + " was saved!", Toast.LENGTH_LONG).show();
                            addNotification();

                        }
                    });
                }
            }
        };
        observer.startWatching();
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_action_gif)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //player.stop();
    }
}