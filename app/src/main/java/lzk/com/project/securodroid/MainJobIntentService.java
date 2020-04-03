package lzk.com.project.securodroid;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;

public class MainJobIntentService extends JobIntentService {
    private static final String TAG = "MainJobIntentService";

    /**
     * static void enqueueWork(Context context, Intent work) : make work enqueued
     * @param context Context
     * @param work work
     */
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MainJobIntentService.class, 123, work);
    }


    /**
     * onCreate Method
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    /**
     * protected void onHandleWork(@NonNull Intent intent) : The main handle
     * @param intent Intent
     */
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");

        boolean usb, headphone, move, shut, airplane, autostart;
        String pnum, mail;
        double latitude, longitude;

        usb = intent.getBooleanExtra("usb", false);
        headphone = intent.getBooleanExtra("headphone", false);
        move = intent.getBooleanExtra("move", false);
        shut = intent.getBooleanExtra("Shut", false);
        airplane = intent.getBooleanExtra("airplane", false);
        autostart = intent.getBooleanExtra("autostart", false);

        pnum = intent.getStringExtra("pnum");
        mail = intent.getStringExtra("mail");

        latitude = intent.getDoubleExtra("latitude", 0.0);
        longitude = intent.getDoubleExtra("longitude", 0.0);


        if (isStopped()) {
            return;
        }

        int i = 0;
        while (i < 1000) {

            if (usb) {

                Intent usbintent = getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

                if (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                    Log.d(TAG, "onHandleWork: CHARGING");
                } else {
                    Log.d(TAG, "onHandleWork: NOT CHARGING");
                    alarm(true);
                    sendtrack(pnum, mail, latitude, longitude);
                }
            }
            if (headphone) {

                String headphoneplug = intent.getStringExtra(AudioManager.ACTION_HEADSET_PLUG);

                if (headphoneplug == AudioManager.ACTION_HEADSET_PLUG) {
                    Log.d(TAG, "onHandleWork: HEADPHONE PLUGGED");
                } else {
                    Log.d(TAG, "onHandleWork: HEADPHONE UNPLUGGED");
                    alarm(true);
                    sendtrack(pnum, mail, latitude, longitude);

                }
            }
            if (move) {
                catchMovement();
            }
            if (shut) {
                catchShutdown();
            }
            if (airplane) {
                String airplanemode = intent.getStringExtra(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                if (airplanemode == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    Log.d(TAG, "onHandleWork: AIRPLANEMODE CHANGED");
                    alarm(true);
                    sendtrack(pnum, mail, latitude, longitude);
                } else {
                    Log.d(TAG, "onHandleWork: AIRPLANEMODE UNCHANGED");

                }
            }
            if (autostart) {
                autostartset(true);
            }
            i++;
        }
    }

    /**
     * To Catch Movements
     */
    private void catchMovement() {
        try{
            Log.d(TAG, "catchMovement: ");
        }catch (Exception e){

        }
    }
    /**
     * To Catch Shutdowns
     */
    private void catchShutdown() {
        try{
            Log.d(TAG, "catchShutdown: ");
        }catch (Exception e){

        }
    }
    /**
     * To Sound Alarms
     */
    private void alarm(boolean status) {

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.alarm);
        mp.start();

        if (!status) {
            mp.stop();
        }

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 5000 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(5000);
        }


    }

    /**
     * private void sendtrack(String pnum, String mail, double latitude, double longitude): Send Tracking Information
     * @param pnum Phone Number
     * @param mail Mail
     * @param latitude Latitude
     * @param longitude Longitude
     */
    private void sendtrack(String pnum, String mail, double latitude, double longitude) {

        Intent phonecall = new Intent(Intent.ACTION_CALL);
        phonecall.setData(Uri.parse("tel:" + pnum));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        getApplicationContext().startActivity(phonecall);


    }

    /**
     * default onDestroy()
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    /**
     * default onStopCurrentWork()
     */
    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork: ");
        return super.onStopCurrentWork();
    }

    /**
     * public void autostartset(boolean status)
     * Check and make auto start set
     * @param status Auto start Status
     */
    public void autostartset(boolean status){
        if (status){

        }else {

        }
    }
}
