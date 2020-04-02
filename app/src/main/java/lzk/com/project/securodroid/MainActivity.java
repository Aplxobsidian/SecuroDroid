package lzk.com.project.securodroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SettingsDialog.SettingsDialogListener {

    GridLayout mainGrid;

    Button btn_enable;
    Button btn_disable;
    Button btn_settings;

    TextView text_status;

    ImageView img_enable;
    ImageView img_disable;

    protected boolean USB_PLUG=false;
    protected boolean HEADPHONE_PLUG=false;
    protected boolean MOVEMENT=false;
    protected boolean SHUTDOWN=false;
    protected boolean AIRPLANE_MODE=false;
    protected boolean AUTOSTART=false;

    protected String callPhone;
    protected String mailAddress;


    private boolean PROTECT_STATUS=false;
    private boolean ALARM_STATUS=false;

    protected int NUSB_PLUG=0;
    protected int NHEADPHONE_PLUG=1;
    protected int NMOVEMENT=2;
    protected int NSHUTDOWN=3;
    protected int NAIRPLANE_MODE=4;
    protected int NAUTOSTART=5;

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
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsDialog();
            }
        });
    }

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


        MainJobIntentService.enqueueWork(this,serviceJobIntent);

        startService(serviceIntent);

    }

    protected void stopProtect(){
        Intent serviceIntent = new Intent(this, MainService.class);
        stopService(serviceIntent);

    }
    protected void alarm(){

    }

    protected void sendTrack(){

    }



    /**
    *  public void openSettingsDialog()
    *  The function that open the settings dialog
    *  user can input their desired phone number and email address
    *
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
}

/* Template */

/*
   Function:
   The function
   u

 */