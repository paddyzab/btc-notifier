package com.paddy.btc.notifier.btc_notifier.storage;


import android.content.Context;
import com.paddy.btc.notifier.btc_notifier.R;

public class CurrencyProvider {

    private final UserDataStorage userDataStorage;
    private final Context context;

    public CurrencyProvider(final Context context) {
        userDataStorage = new UserDataStorage(context);
        this.context = context;
    }


    public String getCurrentCurrency() {
        return userDataStorage.get(context.getString(R.string.currency_key));
    }
}
