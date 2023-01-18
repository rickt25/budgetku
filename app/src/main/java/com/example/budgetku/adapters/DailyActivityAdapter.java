package com.example.budgetku.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetku.R;
import com.example.budgetku.model.object.DailyActivity;

import java.util.List;

public class DailyActivityAdapter extends RecyclerView.Adapter<DailyActivityAdapter.DailyActivityViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<DailyActivity> dailyActivityList;

    public DailyActivityAdapter(List<DailyActivity> dailyActivityList) {
        this.dailyActivityList = dailyActivityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DailyActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_daily_activity, viewGroup, false);
        return new DailyActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyActivityViewHolder itemViewHolder, int i) {
        DailyActivity dailyActivity = dailyActivityList.get(i);

        itemViewHolder.activity_date.setText(dailyActivity.getDate());
        itemViewHolder.total.setText(dailyActivity.getTotal().toString());

        if(Integer.parseInt(itemViewHolder.total.getText().toString()) < 0)
        {
            itemViewHolder.total.setTextColor(Color.RED);
        }
        else
        {
            itemViewHolder.total.setTextColor(Color.GREEN);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                itemViewHolder.rvDailyActivities.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(dailyActivity.getActivities().size());

        ActivityAdapter activityAdapter = new ActivityAdapter(dailyActivity.getActivities());

        activityAdapter.setActivityList(dailyActivity.getActivities());
        itemViewHolder.rvDailyActivities.setLayoutManager(layoutManager);
        itemViewHolder.rvDailyActivities.setAdapter(activityAdapter);
        itemViewHolder.rvDailyActivities.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return dailyActivityList.size();
    }

    class DailyActivityViewHolder extends RecyclerView.ViewHolder {
        private TextView activity_date;
        private TextView total;
        private RecyclerView rvDailyActivities;

        DailyActivityViewHolder(View itemView) {
            super(itemView);
            activity_date = itemView.findViewById(R.id.activity_date);
            total = itemView.findViewById(R.id.total);
            rvDailyActivities = itemView.findViewById(R.id.rvDailyActivities);
        }
    }
}
