package com.example.budgetku.model.object;

import java.util.ArrayList;
import java.util.List;

public class DailyActivity {
    private String date;
    private Integer total;
    private List<Activity> activities;

    public DailyActivity()
    {
        activities = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity)
    {
        this.activities.add(activity);
    }
}
