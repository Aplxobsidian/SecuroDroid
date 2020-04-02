package lzk.com.project.securodroid;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MainService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean usb,headphone,move,shut,airplane,autostart;

        usb=intent.getBooleanExtra("usb",false);
        headphone=intent.getBooleanExtra("headphone",false);
        move=intent.getBooleanExtra("move",false);
        shut=intent.getBooleanExtra("Shut",false);
        airplane=intent.getBooleanExtra("airplane",false);
        autostart=intent.getBooleanExtra("autostart",false);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,"ServiceChannel")
                .setContentTitle("Protect Service")
                .setContentText(""+usb+headphone+move+shut+airplane+autostart)
                .setSmallIcon(R.drawable.lock_green)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

        return START_REDELIVER_INTENT;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
