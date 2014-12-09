package com.paddy.btc.notifier.btc_notifier.backend.api;

import com.paddy.btc.notifier.btc_notifier.backend.models.GetCurrentPriceResponse;
import com.paddy.btc.notifier.btc_notifier.backend.models.SupportedCurrency;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface ICoinbaseAPI {

    // list of supported currencies
    @GET("/supported-currencies.json")
    void getSupportedCurrencies(Callback<List<SupportedCurrency>> callback);

    // current price
    @GET("/currentprice.json")
    Observable<GetCurrentPriceResponse> getCurrentBpi();

    // current price in Currency ISO 4217
    @GET("/currentprice/{code}")
    Observable<GetCurrentPriceResponse> getCurrentPriceResponseForISOCode(@Path("code") String code);

    // historical data
}
