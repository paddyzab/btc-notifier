package com.paddy.btc.notifier.btc_notifier.storage;

import android.content.Context;
import android.content.SharedPreferences;
import com.halfbit.tinybus.Bus;
import com.halfbit.tinybus.TinyBus;
import com.paddy.btc.notifier.btc_notifier.R;
import com.paddy.btc.notifier.btc_notifier.storage.events.CurrencyChangedEvent;

public class UserDataStorage implements KeyValueStorage {

    private final static String PREFERENCES_KEY = "SHARED_PREFERENCES";
    private final SharedPreferences preferences;
    private final Bus bus;
    private final Context context;

    public UserDataStorage(final Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        bus = TinyBus.from(context);
    }


    @Override
    public void write(final String newCurrency) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.currency_key), newCurrency);
        editor.apply();

        bus.post(CurrencyChangedEvent.INSTANCE);
    }

    @Override
    public String get(final String key) {
        return preferences.getString(key, "");
    }
}
