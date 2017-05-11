package io.iaso.iaso;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.iaso.iaso.UserAccountHome.UserAccountHome;
import io.iaso.iaso.core.model.NotificationPreferences;
import io.iaso.iaso.network.async.NotificationResponseTask;
import io.iaso.iaso.network.async.NotificationUpdateTask;

public class NotificationSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button save_notif_button;
    private TextView initialAlarm;
    private TextView currentInitialAlarm;
    private TextView nextAlarm;
    private TextView currentNextAlarm;
    private String id;
    private Spinner selectInitialAlarm;
    private Spinner selectNextAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        save_notif_button=(Button)findViewById(R.id.save_notif_button);
        initialAlarm=(TextView)findViewById(R.id.initial_alarm);
        currentInitialAlarm=(TextView)findViewById(R.id.user_initial_alarm);
        nextAlarm=(TextView) findViewById(R.id.next_alarm);
        currentNextAlarm=(TextView)findViewById(R.id.user_next_alarm);
        selectInitialAlarm = (Spinner) findViewById(R.id.initial_alarm_spinner);
        selectNextAlarm = (Spinner) findViewById(R.id.next_alarm_spinner);

        selectInitialAlarm.setOnItemSelectedListener(this);
        selectNextAlarm.setOnItemSelectedListener(this);
        //spinner drop down
        List<String> items = new ArrayList<String>();
        items.add("2 hours before");
        items.add("1 hour before");
        items.add("30 minutes before");
        items.add("15 minutes before");
        items.add("On time");

        List<String> alarmItems = new ArrayList<String>();
        alarmItems.add("Every 5 minutes");
        alarmItems.add("Every 10 minutes");
        alarmItems.add("Every 15 minutes");
        alarmItems.add("Every 20 minutes");
        alarmItems.add ("Every 30 minutes");
        //adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectInitialAlarm.setAdapter(dataAdapter);

        ArrayAdapter<String> alarmAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alarmItems);
        alarmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectNextAlarm.setAdapter(alarmAdapter);

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
                String temp = Float.toString(init);
                if (init > 2){
                    temp += " minutes before";
                }
                else if (init != 0.0) {
                    temp += " hours before";
                }
                else{
                    temp = "At the time the medicine is taken";
                }
                currentInitialAlarm.setText(temp);
                currentNextAlarm.setText("Every " + Float.toString(60 * Float.parseFloat(response.getAdditional_notif())) + " minutes after the first");
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
                String initial_alarm = selectInitialAlarm.getSelectedItem().toString();
                if (initial_alarm.equalsIgnoreCase("2 hours before")){
                    initial_alarm = "2.0";
                }
                else if (initial_alarm.equalsIgnoreCase("1 hour before")){
                    initial_alarm = "1.0";
                }
                else if (initial_alarm.equalsIgnoreCase("30 minutes before")){
                    initial_alarm = "0.5";
                }
                else if (initial_alarm.equalsIgnoreCase("15 minutes before")){
                    initial_alarm = "0.25";
                }
                else {
                    initial_alarm = "0.0";
                }
                String next_alarm = selectNextAlarm.getSelectedItem().toString();
                    if (next_alarm.equalsIgnoreCase("Every 5 minutes")){
                        next_alarm = "5";
                    }
                    else if (next_alarm.equalsIgnoreCase("Every 10 minutes")){
                        next_alarm = "10";
                    }
                    else if (next_alarm.equalsIgnoreCase("Every 15 minutes")){
                        next_alarm = "15";
                    }
                    else if (next_alarm.equalsIgnoreCase("Every 20 minutes")){
                        next_alarm = "20";
                    }
                    else if (next_alarm.equalsIgnoreCase("Every 30 minutes")){
                        next_alarm = "30";
                    }
                task.execute(id, initial_alarm, Float.toString(Float.parseFloat(next_alarm) / 60));
                Toast.makeText(NotificationSettingsActivity.this, "Changes saved.", Toast.LENGTH_LONG).show();
                String saveNotifSettings = "Saved settings, back to home";
                Intent notifSettingsToHome = new Intent(NotificationSettingsActivity.this, UserAccountHome.class);
                notifSettingsToHome.putExtra("Success", saveNotifSettings);
                startActivity(notifSettingsToHome);
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //on selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        //eh
    }
}
