package lzk.com.project.securodroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class MainJobIntentService extends JobIntentService {
    private static final String TAG="MainJobIntentService";

    static void enqueueWork(Context context, Intent work){
        enqueueWork(context,MainJobIntentService.class,123,work);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");

        boolean usb,headphone,move,shut,airplane,autostart;

        usb=intent.getBooleanExtra("usb",false);
        headphone=intent.getBooleanExtra("headphone",false);
        move=intent.getBooleanExtra("move",false);
        shut=intent.getBooleanExtra("Shut",false);
        airplane=intent.getBooleanExtra("airplane",false);
        autostart=intent.getBooleanExtra("autostart",false);


        if (isStopped()) return;




    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }


    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork: ");
        return super.onStopCurrentWork();
    }
}
