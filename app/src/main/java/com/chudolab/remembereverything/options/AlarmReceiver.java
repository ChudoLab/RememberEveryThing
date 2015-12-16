package com.chudolab.remembereverything.options;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.chudolab.remembereverything.R;
import com.chudolab.remembereverything.main_page.MainPageActivity;


/**
 * Created by Chudo on 22.11.2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int smalIcon = R.drawable.diamond000;
        int notificationId = intent.getIntExtra("noteId", 0);
        String currentNoteText = intent.getStringExtra("currentNoteText");

        Intent intentOpen = new Intent(context, MainPageActivity.class);
        PendingIntent pending = PendingIntent.getActivity(context, 0, intentOpen, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                context)
                .setContentText(currentNoteText)
                .setContentTitle("Your note")
                .setSmallIcon(smalIcon)
                .setAutoCancel(true)
                .setTicker("Another title")
//                .setLargeIcon(largeIcon)
                .setDefaults(Notification.DEFAULT_LIGHTS |
                        Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)

                .setContentIntent(pending);

        Notification notification = notificationBuilder.build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.e("Alarm", "recieved");
        notificationManager.notify(10, notification);

    }
}