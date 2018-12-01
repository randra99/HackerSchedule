package com.example.wildcats;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class HackathonStatusNotificationBar {

    private static String CHANNEL_ID = "HSNC";

    public HackathonStatusNotificationBar(Context context, int startTime, int endTime, int currentTime) {
        createNotificationChannel(context);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationCompat.setContentTitle("Progress bar");
        notificationCompat.setProgress(endTime - startTime, currentTime - startTime,false);

    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
