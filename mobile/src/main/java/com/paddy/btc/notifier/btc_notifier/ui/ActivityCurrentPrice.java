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
import com.paddy.btc.notifier.btc_notifier.backend.actions.CustomCurrencyAction;
import com.paddy.btc.notifier.btc_notifier.backend.actions.LogError;
import com.paddy.btc.notifier.btc_notifier.backend.actions.UpdatePriceAction;
import com.paddy.btc.notifier.btc_notifier.backend.api.ApiProvider;
import com.paddy.btc.notifier.btc_notifier.backend.api.ICoinbaseAPI;
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
import rx.schedulers.Schedulers;

public class ActivityCurrentPrice extends Activity {

    public static final String LOG_TAG = ActivityCurrentPrice.class.getSimpleName();

    public static final int INITIAL_DELAY = 0;
    public static final int POLLING_INTERVAL = 1000 * 60;
    private List<SupportedCurrency> supportedCurrencies = new ArrayList<SupportedCurrency>();
    private CurrencyProvider currencyProvider;
    private CustomCurrencyAction customCurrencyAction;

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
        final ICoinbaseAPI coinbaseAPI = provider.getCoinbaseAPI();
        currencyProvider = new CurrencyProvider(this);
        final CurrentPriceViewModelFactory currentPriceViewModelFactory = new CurrentPriceViewModelFactory(currencyProvider);
        final UpdatePriceAction updatePrice = new UpdatePriceAction(cpViewCurrentPrice, currentPriceViewModelFactory);
        final LogError logError = new LogError();

        customCurrencyAction = new CustomCurrencyAction(currencyProvider, coinbaseAPI, updatePrice, logError);
        periodicalScheduler = Schedulers.newThread().createWorker();
        periodicalScheduler.schedulePeriodically(customCurrencyAction, INITIAL_DELAY, POLLING_INTERVAL, TimeUnit.MILLISECONDS);

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
}
