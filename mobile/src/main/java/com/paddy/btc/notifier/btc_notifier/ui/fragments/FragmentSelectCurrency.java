package com.paddy.btc.notifier.btc_notifier.ui.fragments;

import android.app.Fragment;
import com.paddy.btc.notifier.btc_notifier.backend.models.SupportedCurrency;
import java.util.List;

public class FragmentSelectCurrency extends Fragment {

    private List<SupportedCurrency> supportedCurrencies;

    public void setData(List<SupportedCurrency> data) {
        for (SupportedCurrency currency : data) {
            if (!supportedCurrencies.contains(currency)) {
                supportedCurrencies.add(currency);
            }
        }
    }

}
