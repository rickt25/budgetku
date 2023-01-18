package com.example.budgetku.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.budgetku.R;
import com.example.budgetku.model.object.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletAutoCompleteAdapter extends ArrayAdapter<Wallet> {
    private List<Wallet> wallets;

    public WalletAutoCompleteAdapter(@NonNull Context context, @NonNull List<Wallet> wallets) {
        super(context, 0, wallets);
        this.wallets = new ArrayList<>(wallets);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return walletFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.wallet_autocomplete_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.wallet_name);

        Wallet wallet = getItem(position);

        if (wallet != null) {
            textViewName.setText(wallet.getWallet_name());
        }

        return convertView;
    }

    private Filter walletFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Wallet> suggestions = new ArrayList<Wallet>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(wallets);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Wallet item : wallets) {
                    if (item.getWallet_name().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Wallet) resultValue).getWallet_name();
        }
    };
}
