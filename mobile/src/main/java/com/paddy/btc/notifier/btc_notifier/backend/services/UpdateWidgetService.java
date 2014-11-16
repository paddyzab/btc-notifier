package com.paddy.btc.notifier.btc_notifier.backend.services;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import com.paddy.btc.notifier.btc_notifier.R;
import com.paddy.btc.notifier.btc_notifier.utils.PriceWidgetProvider;
import java.util.Random;

public class UpdateWidgetService extends Service {

    private static String TAG = UpdateWidgetService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        final ComponentName widget = new ComponentName(getApplicationContext(),
                PriceWidgetProvider.class);

        for (int widgetId : allWidgetIds) {
            int number = (new Random().nextInt(100));

            final RemoteViews remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.widget_layout);
            // Set the text
            remoteViews.setTextViewText(R.id.tvCurrentPrice,
                    String.valueOf(number));

            // Register an onClickListener
            final Intent clickIntent = new Intent(this.getApplicationContext(),
                    PriceWidgetProvider.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds);

            final PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.tvCurrentPrice, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
