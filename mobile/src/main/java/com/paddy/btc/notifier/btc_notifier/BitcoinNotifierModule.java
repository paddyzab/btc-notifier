package com.paddy.btc.notifier.btc_notifier;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class BitcoinNotifierModule {
    private final Application application;

    BitcoinNotifierModule(final Application application) {
        this.application = application;
    }

    @Provides @Singleton public Context getApplicationContext() {
        return application;
    }
}
