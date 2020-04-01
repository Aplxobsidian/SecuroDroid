package lzk.com.project.securodroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SettingsDialog extends AppCompatDialogFragment {
    private EditText phonenum;
    private EditText email;
    private SettingsDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_settings,null);

        builder.setView(view)
                .setTitle("Setting")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String str_phonenum  = phonenum.getText().toString();
                        String str_email  = email.getText().toString();

                        listener.applyTexts(str_phonenum,str_email);
                    }
                });

        phonenum = view.findViewById(R.id.number_phone);
        email = view.findViewById(R.id.mail_address);



        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SettingsDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+"Must Implement SettingsDialogListener");
        }
    }

    public interface SettingsDialogListener{
        void applyTexts(String phonenum, String email);
    }
}
