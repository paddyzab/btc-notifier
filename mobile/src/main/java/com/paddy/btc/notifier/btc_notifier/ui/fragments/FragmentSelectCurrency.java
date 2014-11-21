package com.paddy.btc.notifier.btc_notifier.ui.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.paddy.btc.notifier.btc_notifier.R;
import com.paddy.btc.notifier.btc_notifier.backend.models.SupportedCurrency;
import com.paddy.btc.notifier.btc_notifier.ui.adapters.AdapterSelectCurrency;
import java.util.ArrayList;
import java.util.List;

public class FragmentSelectCurrency extends DialogFragment {

    private final static String LIST_KEY = "currencies";
    private List<SupportedCurrency> supportedCurrencies;
    private AdapterSelectCurrency adapterSelectCurrency;

    @InjectView(R.id.listViewSelectCurrency)
    protected ListView selectedCurrency;

    public static FragmentSelectCurrency newInstance(final ArrayList<SupportedCurrency> supportedCurrencies) {
        final FragmentSelectCurrency fragmentSelectCurrency = new FragmentSelectCurrency();
        final Bundle args = new Bundle();
        args.putParcelableArrayList(LIST_KEY, supportedCurrencies);

        fragmentSelectCurrency.setArguments(args);

        return fragmentSelectCurrency;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportedCurrencies = getArguments().getParcelableArrayList(LIST_KEY);
        adapterSelectCurrency = new AdapterSelectCurrency(supportedCurrencies);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_select_currency, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedCurrency.setAdapter(adapterSelectCurrency);
    }

    public void setData(List<SupportedCurrency> data) {
        for (SupportedCurrency currency : data) {
            if (!supportedCurrencies.contains(currency)) {
                supportedCurrencies.add(currency);
            }
        }
    }

}
