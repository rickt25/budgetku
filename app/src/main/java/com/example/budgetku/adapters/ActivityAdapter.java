package com.example.budgetku.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetku.R;
import com.example.budgetku.model.object.Activity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private List<Activity> activityList;

    ActivityAdapter(List<Activity> activityList) {
        this.activityList = activityList;
        Integer size = this.activityList.size();
        Log.d("total", size.toString()  );
        notifyDataSetChanged();
    }

    public void setActivityList(List<Activity> activityList)
    {
        this.activityList = activityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activity, viewGroup, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder activityViewHolder, int i) {
        Activity activity = activityList.get(i);
        activityViewHolder.wallet_name.setText(activity.getWallet_name());
        activityViewHolder.category_name.setText(activity.getCategory_name());
        activityViewHolder.amount.setText(currencyRupiah(activity.getAmount().toString()));

        if(activity.getActivity_type().equals("expense"))
        {
            activityViewHolder.amount.setTextColor(Color.RED);
        }else
        {
            activityViewHolder.amount.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;
        TextView wallet_name;
        TextView amount;

        ActivityViewHolder(View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.category);
            wallet_name = itemView.findViewById(R.id.wallet);
            amount = itemView.findViewById(R.id.amount);
        }
    }

    private String currencyRupiah(String amount){
        Double money = Double.parseDouble(amount);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(money);
    }
}
