package com.example.wildcats;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class NotificationHelper
{
    private static NotificationManagerCompat notificationManager;
    private static Context context;
    private static final String CHANNEL_ID = "Ch0";

    public static void setContext(Context contextIn)
    {
        notificationManager = NotificationManagerCompat.from(contextIn);
        context = contextIn;
    }

    public static void showStatusBarNotification(int id, int startTime, int endTime, int currentTime)
    {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_ID);
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notification.setContentTitle(getHackathonTime(startTime, endTime));
        notification.setProgress(endTime - startTime, currentTime - startTime, false);
        notification.setOngoing(true);

        notificationManager.notify(id, notification.build());
    }

    private static String getHackathonTime(int startTime, int endTime)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        StringBuffer time = new StringBuffer();
        time.append(String.format("%02d:%02d", startTime / 60, startTime % 60));

        for(int pixel =0; pixel <= deviceWidth; pixel += 22)
        {
            time.append(" ");
        }

        time.append(String.format("%02d:%02d", endTime / 60, endTime % 60));
        return time.toString();
    }

    public static void showEventNotification(int id, String eventName)
    {
        Intent removeNotification = new Intent(context, ActionReceiver.class);
        removeNotification.putExtra("id", id);

        PendingIntent removeNotificationPendingIntent = PendingIntent.getBroadcast(context, 1, removeNotification, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_ID);
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notification.setContentTitle(eventName);
        notification.setOngoing(true);
        notification.setContentIntent(removeNotificationPendingIntent);

        notification.addAction(R.drawable.notification_done, "Done", removeNotificationPendingIntent);

        notificationManager.notify(id, notification.build());
    }

    public static void createNotificationChannel()
    {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void removeNotification(int id)
    {
        notificationManager.cancel(id);
    }
}
