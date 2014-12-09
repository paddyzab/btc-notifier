package com.paddy.btc.notifier.btc_notifier.storage;


import android.content.Context;
import com.paddy.btc.notifier.btc_notifier.R;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;

public class CurrencyProvider {

    private final UserDataStorage userDataStorage;
    private final Context context;

    public CurrencyProvider(final Context context) {
        userDataStorage = new UserDataStorage(context);
        this.context = context;
    }


    public String getCurrentCurrency() {
        if (!StringUtils.isEmpty(userDataStorage.get(context.getString(R.string.currency_key)))) {
            return userDataStorage.get(context.getString(R.string.currency_key));
        } else {
            return resolveFromLocale();
        }
    }

    private String resolveFromLocale() {
        final Locale locale = context.getResources().getConfiguration().locale;
        return locale.getISO3Country();
    }
}
