package lzk.com.project.securodroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SettingsDialog.SettingsDialogListener {

    /**
     * Permission Related Codes
     */
    private int PERMISSION_FINELOC_CODE = 1;
    private int PERMISSION_COARSELOC_CODE = 2;
    private int PERMISSION_INTERNET_CODE = 3;
    private int PERMISSION_CALL_CODE= 4;

    /**
     * LocationManager
     */
    protected LocationManager locationManager;


    /**
     * Interface Layout Elements
     */
    GridLayout mainGrid;

    Button btn_enable;
    Button btn_disable;
    Button btn_settings;

    TextView text_status;

    ImageView img_enable;
    ImageView img_disable;

    /**
     * Status Variables
     */
    protected boolean USB_PLUG=false;
    protected boolean HEADPHONE_PLUG=false;
    protected boolean MOVEMENT=false;
    protected boolean SHUTDOWN=false;
    protected boolean AIRPLANE_MODE=false;
    protected boolean AUTOSTART=false;

    /**
     * User-defined Variables
     */
    protected String callPhone;
    protected String mailAddress;

    /**
     * Location Info
     */
    protected double latitude,longitude;

    private boolean PROTECT_STATUS=false;
    private boolean ALARM_STATUS=false;

    /**
     * Reserved Codes
     */
    protected int NUSB_PLUG=0;
    protected int NHEADPHONE_PLUG=1;
    protected int NMOVEMENT=2;
    protected int NSHUTDOWN=3;
    protected int NAIRPLANE_MODE=4;
    protected int NAUTOSTART=5;

    /**
     * Override Default onCreate Method.
     * @param savedInstanceState Default savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainGrid = findViewById(R.id.mainGrid);

        //setSingleEvent(mainGrid);
        setToggleEvent(mainGrid);


        btn_enable = findViewById(R.id.enable_function_btn);
        btn_disable = findViewById(R.id.disable_function_btn);
        btn_settings = findViewById(R.id.settings_btn);

        text_status = findViewById(R.id.protect_status);

        img_enable = findViewById(R.id.is_protected_img);
        img_disable = findViewById(R.id.not_protected_img);
        /*
          LocationListener for Location Update
         */
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               latitude=location.getLatitude();
               longitude=location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        /*
          Check Permissions
         */
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.INTERNET)==PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED
        ){
            try{
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            requestFineLocPermission();
            requestCoarseLocPermission();
            requestInternetPermission();
            requestCallPermission();
        }


        /*
           btn_enable.setOnClickListener
           setOnClickListener for enable service

         */
        btn_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_status.setText(R.string.is_protected);
                img_enable.setVisibility(View.VISIBLE);
                img_disable.setVisibility(View.INVISIBLE);
                PROTECT_STATUS=true;
                protectProcess();
            }
        });

        /*
           btn_disable.setOnClickListener
           setOnClickListener for disable button

         */
        btn_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    stopProtect();
                    PROTECT_STATUS=false;
                    ALARM_STATUS=false;
                    text_status.setText(R.string.not_protected);
                    img_disable.setVisibility(View.VISIBLE);
                    img_enable.setVisibility(View.INVISIBLE);
            }
        });

        /*
           btn_settings.setOnClickListener
           setOnClickListener for settings button

         */
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsDialog();
            }
        });
    }


    /**
     *  private void setToggleEvent(GridLayout mainGrid)
     *  The function that is used for toggle grids, the actual usage
     * @param mainGrid The grids in user layout
     */
    private void setToggleEvent(GridLayout mainGrid) {
        for (int i=0; i<mainGrid.getChildCount();i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cardView.getCardBackgroundColor().getDefaultColor()==getColor(R.color.colorAccent)){
                        cardView.setCardBackgroundColor(getColor(R.color.Orange));
                        //Toast.makeText(MainActivity.this,"State: Toggled",Toast.LENGTH_SHORT).show();
                        if (cardView.getId()==R.id.card_usb){
                            Toast.makeText(MainActivity.this,"YES",Toast.LENGTH_SHORT).show();
                            USB_PLUG=true;
                        }
                        if (cardView.getId()==R.id.card_headphone){
                            Toast.makeText(MainActivity.this,"YES",Toast.LENGTH_SHORT).show();
                            HEADPHONE_PLUG=true;
                        }

                        if (cardView.getId()==R.id.card_movement){
                            Toast.makeText(MainActivity.this,"YES",Toast.LENGTH_SHORT).show();
                            MOVEMENT=true;
                        }
                        if (cardView.getId()==R.id.card_shutdown){
                            Toast.makeText(MainActivity.this,"YES",Toast.LENGTH_SHORT).show();
                            SHUTDOWN=true;
                        }
                        if (cardView.getId()==R.id.card_airplane){
                            Toast.makeText(MainActivity.this,"YES",Toast.LENGTH_SHORT).show();
                            AIRPLANE_MODE=true;
                        }

                        if (cardView.getId()==R.id.card_autostart){
                            Toast.makeText(MainActivity.this,"YES",Toast.LENGTH_SHORT).show();
                            AUTOSTART=true;
                        }

                    }
                    else {
                        cardView.setCardBackgroundColor(getColor(R.color.colorAccent));
                        //Toast.makeText(MainActivity.this,"State: Not Toggled",Toast.LENGTH_SHORT).show();
                        if (cardView.getId()==R.id.card_usb){
                            Toast.makeText(MainActivity.this,"NO",Toast.LENGTH_SHORT).show();
                            USB_PLUG=false;
                        }
                        if (cardView.getId()==R.id.card_headphone){
                            Toast.makeText(MainActivity.this,"NO",Toast.LENGTH_SHORT).show();
                            HEADPHONE_PLUG=false;
                        }

                        if (cardView.getId()==R.id.card_movement){
                            Toast.makeText(MainActivity.this,"NO",Toast.LENGTH_SHORT).show();
                            MOVEMENT=false;
                        }
                        if (cardView.getId()==R.id.card_shutdown){
                            Toast.makeText(MainActivity.this,"NO",Toast.LENGTH_SHORT).show();
                            SHUTDOWN=false;
                        }
                        if (cardView.getId()==R.id.card_airplane){
                            Toast.makeText(MainActivity.this,"NO",Toast.LENGTH_SHORT).show();
                            AIRPLANE_MODE=false;
                        }

                        if (cardView.getId()==R.id.card_autostart){
                            Toast.makeText(MainActivity.this,"NO",Toast.LENGTH_SHORT).show();
                            AUTOSTART=false;
                        }
                    }

                }
            });

        }
    }

    /**
     *  private void setSingleEvent(GridLayout mainGrid)
     *  The function that is used for single testing, not actual use
     * @param mainGrid The grids in user layout
     */
    private void setSingleEvent(GridLayout mainGrid) {

        for (int i=0; i<mainGrid.getChildCount();i++){

            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"Clicked at index "+ finalI, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /**
     * protected void protectProcess() The protective process
     *
     */
    protected void protectProcess(){

        Intent serviceJobIntent = new Intent(this,MainJobIntentService.class);
        Intent serviceIntent = new Intent(this, MainService.class);
        serviceIntent.putExtra("usb",USB_PLUG);
        serviceIntent.putExtra("headphone",HEADPHONE_PLUG);
        serviceIntent.putExtra("move",MOVEMENT);
        serviceIntent.putExtra("Shut",SHUTDOWN);
        serviceIntent.putExtra("airplane",AIRPLANE_MODE);
        serviceIntent.putExtra("autostart",AUTOSTART);

        serviceJobIntent.putExtra("usb",USB_PLUG);
        serviceJobIntent.putExtra("headphone",HEADPHONE_PLUG);
        serviceJobIntent.putExtra("move",MOVEMENT);
        serviceJobIntent.putExtra("Shut",SHUTDOWN);
        serviceJobIntent.putExtra("airplane",AIRPLANE_MODE);
        serviceJobIntent.putExtra("autostart",AUTOSTART);

        serviceJobIntent.putExtra("pnum",callPhone);
        serviceJobIntent.putExtra("mail",mailAddress);

        serviceJobIntent.putExtra("latitude",latitude);
        serviceJobIntent.putExtra("longitude",longitude);

        MainJobIntentService.enqueueWork(this,serviceJobIntent);
        startService(serviceIntent);

    }


    /**
     * protected void stopProtect() The stop protect process
     */
    protected void stopProtect(){
        Intent serviceIntent = new Intent(this, MainService.class);
        stopService(serviceIntent);
    }

    /**
    *  public void openSettingsDialog()
    *  The function that open the settings dialog
    *  user can input their desired phone number and email address
    */
    public void openSettingsDialog(){

        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.show(getSupportFragmentManager(),"Settings");
    }

    /**
     *  public void applyTexts(String phonenum, String email)
     *  The function that return the settings dialog content to the class strings
     * @param phonenum User set phone number
     * @param email User set email address
     *
     */
    @Override
    public void applyTexts(String phonenum, String email) {
        callPhone=phonenum;
        mailAddress=email;
        Toast.makeText(MainActivity.this,""+phonenum+email,Toast.LENGTH_SHORT).show();
    }


    /**
     * public void autostartset(boolean bl) Set Auto start status
     * @param bl Status boolean
     */
    public void autostartset(boolean bl){
        this.AUTOSTART= bl == true;
    }


    /**
     * private void requestFineLocPermission()
     * private void requestCoarseLocPermission()
     * private void requestInternetPermission()
     * private void requestCallPermission
     * Request Multiple Permissions
     */
    private void requestFineLocPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to let us know the survey position and differentiate these positions, no privacy info is gathered")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_FINELOC_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();


        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_FINELOC_CODE);
        }
    }

    private void requestCoarseLocPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to let us know the survey position and differentiate these positions, no privacy info is gathered")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_COARSELOC_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();


        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_COARSELOC_CODE);
        }
    }

    private void requestInternetPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to let us know the survey position and differentiate these positions, no privacy info is gathered")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET},PERMISSION_INTERNET_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();


        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},PERMISSION_INTERNET_CODE);
        }
    }

    private void requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to make emergency calls, no privacy info is gathered")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CALL_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();


        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CALL_CODE);
        }
    }


    /**
     * public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
     * The override ReqPermissionResult Handler
     * @param requestCode The request code
     * @param permissions Permissions
     * @param grantResults Grant results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"FINE LOCATION Granted",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,"FINE LOCATION Not Granted",Toast.LENGTH_SHORT).show();

                }
                return;
            }
            case 2:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"COARSE LOCATION Granted",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,"COARSE LOCATION Not Granted",Toast.LENGTH_SHORT).show();

                }
                return;

            }
            case 3:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"INTERNET LOCATION Granted",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,"INTERNET LOCATION Not Granted",Toast.LENGTH_SHORT).show();

                }
                return;
            }

            case 4:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"CALL  Granted",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,"CALL Not Granted",Toast.LENGTH_SHORT).show();

                }
                return;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

}
