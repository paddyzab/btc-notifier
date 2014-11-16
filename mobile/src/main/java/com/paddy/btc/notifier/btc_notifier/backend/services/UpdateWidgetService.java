package com.paddy.btc.notifier.btc_notifier.backend.services;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import com.paddy.btc.notifier.btc_notifier.R;
import com.paddy.btc.notifier.btc_notifier.backend.api.ApiProvider;
import com.paddy.btc.notifier.btc_notifier.backend.api.ICoinbaseAPI;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.utils.PriceWidgetProvider;
import java.util.concurrent.TimeUnit;
import retrofit.RetrofitError;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UpdateWidgetService extends Service {

    private static String LOG_TAG = UpdateWidgetService.class.getSimpleName();
    private ICoinbaseAPI coinbaseAPI;
    public static final int INITIAL_DELAY = 0;
    public static final int POLLING_INTERVAL = 1000 * 60;
    private RemoteViews remoteViews;
    private AppWidgetManager appWidgetManager;
    private int[] allWidgetIds;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (int widgetId : allWidgetIds) {
            remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.widget_layout);

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

    @Override
    public void onCreate() {
        final ApiProvider provider = new ApiProvider();
        coinbaseAPI = provider.getCoinbaseAPI();

        final Scheduler.Worker periodicalScheduler = Schedulers.newThread().createWorker();
        periodicalScheduler.schedulePeriodically(scheduledPriceAction, INITIAL_DELAY, POLLING_INTERVAL, TimeUnit.MILLISECONDS);
    }

    final Action0 scheduledPriceAction = new Action0() {
        @Override
        public void call() {
            coinbaseAPI.getCurrentBpi().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(currentPriceActon, errorAction);
        }
    };

    final Action1<GetCurrentPriceResponse> currentPriceActon = new Action1<GetCurrentPriceResponse>() {

        private float rate;
        private String updatedAt;

        @Override
        public void call(GetCurrentPriceResponse response) {
            rate = response.getBPIs().getBpiForUsd().getRate_float();
            updatedAt = response.getTime().getUpdated();

            remoteViews.setTextViewText(R.id.tvCurrentPrice, String.valueOf(rate));
            remoteViews.setTextViewText(R.id.tvUpdatedAt, updatedAt);

            //update is needed in order to see the changes
            for (int widgetId : allWidgetIds) {
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
    };

    final Action1<Throwable> errorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            RetrofitError retrofitError = (RetrofitError) throwable;
            Log.d(LOG_TAG, "something went wrong." + retrofitError.getBody());
        }
    };
}
