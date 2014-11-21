package com.paddy.btc.notifier.btc_notifier.ui.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.paddy.btc.notifier.btc_notifier.backend.models.SupportedCurrency;
import com.paddy.btc.notifier.btc_notifier.ui.views.ViewCurrency;
import java.util.List;

public class AdapterSelectCurrency extends BaseAdapter {

    private final List<SupportedCurrency> supportedCurrencies;


    public AdapterSelectCurrency(final List<SupportedCurrency> supportedCurrencies) {
        this.supportedCurrencies = supportedCurrencies;
    }

    @Override
    public int getCount() {
        return supportedCurrencies.size();
    }

    @Override
    public SupportedCurrency getItem(int position) {
        return supportedCurrencies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewCurrency currencyView = (ViewCurrency) convertView;

        if (currencyView == null) {
            currencyView = new ViewCurrency(parent.getContext());
        }
        return currencyView.displayCurrencyData(getItem(position));
    }
}
