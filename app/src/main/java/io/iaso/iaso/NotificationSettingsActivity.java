package io.iaso.iaso;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.core.model.NotificationPreferences;
import io.iaso.iaso.network.async.NotificationResponseTask;
import io.iaso.iaso.network.async.NotificationUpdateTask;

public class NotificationSettingsActivity extends AppCompatActivity {
    private Button save_notif_button;
    private TextView notificationType;
    private TextView initialAlarm;
    private EditText enterNotifType;
    private EditText enterInitialAlarm;
    private TextView nextAlarm;
    private EditText enterNextAlarm;
    private String id;
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

        String magicalTokenOfDestiny = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjU5MDYzYjRhMDMzZmI1MjM2NGIyNWJiZCIsImlhdCI6MTQ5MzU4MTA5M30.wv9cNaZf1HAjj4Pt8VZUHj-MulM9ee1CEWVu-kKZB0I";

        NotificationResponseTask task = new NotificationResponseTask();
        task.setOnNotificationCallbackListener(new NotificationResponseTask.OnNotificationCallbackListener() {
            @Override
            public void onCallBack(NotificationPreferences response) {
                float init = Float.parseFloat(response.getInitial_notif());
                init = 60 * init;
                if (init >= 60){
                    init = init / 60;
                }
                enterInitialAlarm.setText(Float.toString(init));
                enterNextAlarm.setText(Float.toString(60 * Float.parseFloat(response.getAdditional_notif())));
                id = response.getID();
            }
        });

        task.execute(magicalTokenOfDestiny);

        save_notif_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click save, go back to Home UserUpdateTask task = new UserUpdateTask();
                NotificationUpdateTask task = new NotificationUpdateTask();
                task.setOnNotificationUpdateCallbackListener(new NotificationUpdateTask.OnNotificationUpdateCallbackListener() {
                    @Override
                    public void onCallBack(NotificationPreferences response) {
                        //do nothing?
                    }
                });
                task.execute(id, enterInitialAlarm.getText().toString(), Float.toString(Float.parseFloat(enterNextAlarm.getText().toString()) / 60));
                Toast.makeText(NotificationSettingsActivity.this, "Changes saved.", Toast.LENGTH_LONG).show();
                String saveNotifSettings = "Saved settings, back to home";
                Intent notifSettingsToHome = new Intent(NotificationSettingsActivity.this, UserAccountHome.class);
                notifSettingsToHome.putExtra("Success", saveNotifSettings);
                startActivity(notifSettingsToHome);
            }
        });

    }
}
