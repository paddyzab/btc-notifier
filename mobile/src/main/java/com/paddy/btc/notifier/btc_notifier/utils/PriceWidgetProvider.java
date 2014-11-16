package com.paddy.btc.notifier.btc_notifier.utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.paddy.btc.notifier.btc_notifier.R;
import java.util.Random;

public class PriceWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context,
                PriceWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            //TODO: Replace this number with the one from the API
            int number = (new Random().nextInt(100));

            final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            // Set the text
            remoteViews.setTextViewText(R.id.tvCurrentPrice, String.valueOf(number));

            final Intent intent = new Intent(context, PriceWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            final PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.tvCurrentPrice, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

    }
}
