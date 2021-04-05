package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.zendrive.sdk.*;

public abstract class MyZendriveNotificationProvider implements ZendriveNotificationProvider {
    private static final int ZENDRIVE_NOTIFICATION_ID = 495;

    private void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            NotificationChannel foregroundNotificationChannel = new NotificationChannel
                    ("1", "Zendrive trip tracking",
                            NotificationManager.IMPORTANCE_DEFAULT);
            foregroundNotificationChannel.setShowBadge(false);
            NotificationChannel issuesNotificationChannel = new NotificationChannel
                    ("2", "Issues",
                            NotificationManager.IMPORTANCE_DEFAULT);
            issuesNotificationChannel.setShowBadge(true);

            if (manager != null) {
                manager.createNotificationChannel(foregroundNotificationChannel);
                manager.createNotificationChannel(issuesNotificationChannel);
            }
        }
    }

    private Notification getInDriveNotification(@NonNull Context context) {
        createNotificationChannels(context);
        return new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(context.getString(R.string.app_name))
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentText("Drive Active.").build();
    }

    private Notification getMaybeInDriveNotification(@NonNull Context context) {
        createNotificationChannels(context);
        return new NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(context.getString(R.string.app_name))
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentText("Detecting Possible Drive.").build();
    }

    @NonNull
    @Override
    public ZendriveNotificationContainer getMaybeInDriveNotificationContainer(@NonNull Context context) {
        return new ZendriveNotificationContainer(
                ZENDRIVE_NOTIFICATION_ID,
                getMaybeInDriveNotification(context));
    }

    @NonNull
    @Override
    public ZendriveNotificationContainer getInDriveNotificationContainer(@NonNull Context context) {
        return new ZendriveNotificationContainer(
                ZENDRIVE_NOTIFICATION_ID,
                getInDriveNotification(context));
    }
}
