package com.example.digitalpeek;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class lockScreenReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {



            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)&& !MainActivity.container.isEmpty()) {


                String textContent=getPeek();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context,MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Peek me")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(textContent))
                        .setAutoCancel(true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

                NotificationManagerCompat manager = NotificationManagerCompat.from(context);
                manager.notify(1,builder.build());

                MainActivity.notificationIssued=true;

            }



        }


    public String getPeek()
    {
        String temp="";
        for(int i=0;i<MainActivity.container.size();i++)
        {
            temp+=MainActivity.container.get(i);
            temp+="\n";

        }
        return temp;
    }

}
