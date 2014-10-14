package com.paddy.btc.notifier.btc_notifier.backend.api;

import com.paddy.btc.notifier.btc_notifier.BuildConfig;
import retrofit.RestAdapter;

public class ApiProvider {

   private ICoinbaseAPI coinbaseAPI;

    public ApiProvider() {
        initCoinbaseApi();
    }

    public ICoinbaseAPI getCoinbaseAPI() {
        return coinbaseAPI;
    }

    private void initCoinbaseApi() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BuildConfig.API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.valueOf(BuildConfig.API_LOG_LEVEL))
                .build();

        coinbaseAPI = restAdapter.create(ICoinbaseAPI.class);
    }
}
