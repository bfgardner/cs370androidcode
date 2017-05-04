package com.example.iaso.iaso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iaso.iaso.R;

import org.w3c.dom.Text;

public class NotificationSettingsActivity extends AppCompatActivity {
    private Button save_notif_button;
    private TextView notificationType;
    private TextView initialAlarm;
    private EditText enterNotifType;
    private EditText enterInitialAlarm;
    private TextView nextAlarm;
    private EditText enterNextAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        save_notif_button=(Button)findViewById(R.id.save_notif_button);
        notificationType = (TextView)findViewById(R.id.notif_type);
        initialAlarm=(TextView)findViewById(R.id.initial_alarm);
        enterNotifType=(EditText)findViewById(R.id.user_notif_type);
        enterInitialAlarm=(EditText)findViewById(R.id.user_initial_alarm);
        nextAlarm=(TextView) findViewById(R.id.next_alarm);
        enterNextAlarm=(EditText)findViewById(R.id.user_next_alarm);

        save_notif_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click save, go back to Home
                String saveNotifSettings = "Saved settings, back to home";
                Intent notifSettingsToHome = new Intent(NotificationSettingsActivity.this, UserAccountHome.class);
                notifSettingsToHome.putExtra("Success", saveNotifSettings);
                startActivity(notifSettingsToHome);
            }
        });

    }
}
