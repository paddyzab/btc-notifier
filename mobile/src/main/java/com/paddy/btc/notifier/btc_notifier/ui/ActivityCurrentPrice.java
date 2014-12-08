package com.paddy.btc.notifier.btc_notifier.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.common.collect.Lists;
import com.halfbit.tinybus.Bus;
import com.halfbit.tinybus.Subscribe;
import com.halfbit.tinybus.TinyBus;
import com.paddy.btc.notifier.btc_notifier.R;
import com.paddy.btc.notifier.btc_notifier.backend.actions.LogError;
import com.paddy.btc.notifier.btc_notifier.backend.actions.ScheduledPriceAction;
import com.paddy.btc.notifier.btc_notifier.backend.actions.UpdatePriceAction;
import com.paddy.btc.notifier.btc_notifier.backend.api.ApiProvider;
import com.paddy.btc.notifier.btc_notifier.backend.api.ICoinbaseAPI;
import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.backend.models.SupportedCurrency;
import com.paddy.btc.notifier.btc_notifier.storage.CurrencyProvider;
import com.paddy.btc.notifier.btc_notifier.storage.events.CurrencyChangedEvent;
import com.paddy.btc.notifier.btc_notifier.ui.factories.CurrentPriceViewModelFactory;
import com.paddy.btc.notifier.btc_notifier.ui.fragments.FragmentSelectCurrency;
import com.paddy.btc.notifier.btc_notifier.ui.views.ViewCurrentPrice;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ActivityCurrentPrice extends Activity {

    public static final String LOG_TAG = ActivityCurrentPrice.class.getSimpleName();

    public static final int INITIAL_DELAY = 0;
    public static final int POLLING_INTERVAL = 1000 * 60;
    private ICoinbaseAPI coinbaseAPI;
    private CurrentPriceViewModelFactory currentPriceViewModelFactory;
    private List<SupportedCurrency> supportedCurrencies = new ArrayList<SupportedCurrency>();
    private CurrencyProvider currencyProvider;
    private UpdatePriceAction updatePriceAction;
    private LogError logError;
    private ScheduledPriceAction scheduledPriceAction;

    @InjectView(R.id.cpCurrentPriceView)
    protected ViewCurrentPrice cpViewCurrentPrice;

    @InjectView(R.id.textViewSelectedCurrency)
    protected TextView textViewSelectedCurrency;

    private Scheduler.Worker periodicalScheduler;
    private Bus bus;

    @OnClick(R.id.buttonSelectCurrency)
    void selectCurrency() {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        final Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        if (!supportedCurrencies.isEmpty()) {
            DialogFragment newFragment = FragmentSelectCurrency.newInstance(Lists.newArrayList(supportedCurrencies));
            newFragment.show(ft, "dialog");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_price);

        ButterKnife.inject(this);

        final ApiProvider provider = new ApiProvider();
        coinbaseAPI = provider.getCoinbaseAPI();
        currencyProvider = new CurrencyProvider(this);
        currentPriceViewModelFactory = new CurrentPriceViewModelFactory(currencyProvider);
        updatePriceAction = new UpdatePriceAction(cpViewCurrentPrice, currentPriceViewModelFactory);
        logError = new LogError();
        scheduledPriceAction = new ScheduledPriceAction(coinbaseAPI, updatePriceAction, logError);

        periodicalScheduler = Schedulers.newThread().createWorker();
        periodicalScheduler.schedulePeriodically(scheduledPriceAction, INITIAL_DELAY, POLLING_INTERVAL, TimeUnit.MILLISECONDS);

        // TODO: 1. create provider for all backend related data
        // TODO: 3. update all views accordingly when changing the Currency
        coinbaseAPI.getSupportedCurrencies(new Callback<List<SupportedCurrency>>() {
            @Override
            public void success(final List<SupportedCurrency> supportedCurrencies, final Response response) {
                ActivityCurrentPrice.this.supportedCurrencies.addAll(supportedCurrencies);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, "something went wrong." + error.getBody());
            }
        });

        bus = TinyBus.from(this);

        textViewSelectedCurrency.setText(currencyProvider.getCurrentCurrency());
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void onCurrencyChanged(final CurrencyChangedEvent event) {
        textViewSelectedCurrency.setText(currencyProvider.getCurrentCurrency());
        periodicalScheduler.unsubscribe();

        periodicalScheduler = Schedulers.newThread().createWorker();
        periodicalScheduler.schedulePeriodically(customCurrencyAction, INITIAL_DELAY, POLLING_INTERVAL, TimeUnit.MILLISECONDS);
    }


    final Action0 customCurrencyAction = new Action0() {

        @Override
        public void call() {
            coinbaseAPI.getCurrentPriceResponseForISOCode(currencyProvider.getCurrentCurrency()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(updatePrice, logError);
        }
    };

    final Action1<GetCurrentPriceResponse> updatePrice = new Action1<GetCurrentPriceResponse>() {
        @Override
        public void call(GetCurrentPriceResponse response) {
            cpViewCurrentPrice.updateDataModel(currentPriceViewModelFactory.getCurrentPriceModel(response));
        }
    };
}
