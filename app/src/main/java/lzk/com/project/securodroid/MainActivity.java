package lzk.com.project.securodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_enable;
    Button btn_disable;

    TextView text_status;

    ImageView img_enable;
    ImageView img_disable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




        btn_enable = findViewById(R.id.enable_function_btn);
        btn_disable = findViewById(R.id.disable_function_btn);

        text_status = findViewById(R.id.protect_status);

        img_enable = findViewById(R.id.is_protected_img);
        img_disable = findViewById(R.id.not_protected_img);

        btn_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_status.setText(R.string.is_protected);
                img_enable.setVisibility(View.VISIBLE);
                img_disable.setVisibility(View.INVISIBLE);

            }
        });
        btn_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_status.setText(R.string.not_protected);
                img_disable.setVisibility(View.VISIBLE);
                img_enable.setVisibility(View.INVISIBLE);
            }
        });

    }


}
