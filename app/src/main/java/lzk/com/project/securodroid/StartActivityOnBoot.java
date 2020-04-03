package lzk.com.project.securodroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartActivityOnBoot extends BroadcastReceiver {

    /**
     * Auto Start On Boot onReceive Receiver
     * @param context The context
     * @param intent The Intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){

            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
