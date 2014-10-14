package com.paddy.btc.notifier.btc_notifier.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.paddy.btc.notifier.btc_notifier.R;
import com.paddy.btc.notifier.btc_notifier.backend.api.ApiProvider;
import com.paddy.btc.notifier.btc_notifier.backend.api.ICoinbaseAPI;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.ui.factories.CurrentPriceViewModelFactory;
import com.paddy.btc.notifier.btc_notifier.ui.views.CurrentPriceView;
import java.util.concurrent.TimeUnit;
import retrofit.RetrofitError;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ActivityCurrentPrice extends Activity {

    public static final String TAG = ActivityCurrentPrice.class.getSimpleName();

    public static final int INITIAL_DELAY = 0;
    public static final int POLLING_INTERVAL = 1000 * 60;
    private ICoinbaseAPI coinbaseAPI;
    private CurrentPriceViewModelFactory currentPriceViewModelFactory;

    @InjectView(R.id.cpCurrentPriceView)
    protected CurrentPriceView cpCurrentPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_price);

        ButterKnife.inject(this);

        final ApiProvider provider = new ApiProvider();
        coinbaseAPI = provider.getCoinbaseAPI();

        final Scheduler.Worker periodicalScheduler = Schedulers.newThread().createWorker();
        periodicalScheduler.schedulePeriodically(scheduledPriceAction, INITIAL_DELAY, POLLING_INTERVAL, TimeUnit.MILLISECONDS);

        currentPriceViewModelFactory = new CurrentPriceViewModelFactory();
    }


    final Action0 scheduledPriceAction = new Action0() {
        @Override
        public void call() {
            coinbaseAPI.getCurrentBpi().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(currentPriceActon, errorAction);
        }
    };

    final Action1<GetCurrentPriceResponse> currentPriceActon = new Action1<GetCurrentPriceResponse>() {
        @Override
        public void call(GetCurrentPriceResponse response) {
            cpCurrentPriceView.updateDataModel(currentPriceViewModelFactory.getCurrentPriceModel(response));
        }
    };

    final Action1<Throwable> errorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            RetrofitError retrofitError = (RetrofitError) throwable;
            Log.d(TAG, "something went wrong." + retrofitError.getBody());
        }
    };
}
