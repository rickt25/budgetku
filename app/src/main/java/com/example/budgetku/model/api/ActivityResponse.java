package com.example.budgetku.model.api;

import com.example.budgetku.model.object.DailyActivity;

import java.util.List;

public class ActivityResponse {
    private List<DailyActivity> data;

    public List<DailyActivity> getData() {
        return data;
    }

    public void setData(List<DailyActivity> data) {
        this.data = data;
    }
}
