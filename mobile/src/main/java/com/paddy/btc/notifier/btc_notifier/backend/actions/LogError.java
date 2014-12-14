package com.paddy.btc.notifier.btc_notifier.backend.actions;

import android.util.Log;
import retrofit.RetrofitError;
import rx.functions.Action1;

public class LogError implements Action1<Throwable> {

    @Override
    public void call(Throwable throwable) {
        RetrofitError retrofitError = (RetrofitError) throwable;
        Log.d("DEBUG_TAG", "something went wrong. " + retrofitError.toString());
    }
}
