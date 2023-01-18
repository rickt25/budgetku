package com.example.budgetku.fragments;

import static com.example.budgetku.activities.LoginActivity.token;
import static com.example.budgetku.activities.LoginActivity.username;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetku.Api.ApiClient;
import com.example.budgetku.R;
import com.example.budgetku.activities.LoginActivity;
import com.example.budgetku.adapters.DailyActivityAdapter;
import com.example.budgetku.model.api.ActivityResponse;
import com.example.budgetku.model.api.OverviewResponse;
import com.example.budgetku.model.api.TodayActivityResponse;
import com.example.budgetku.model.object.DailyActivity;
import com.example.budgetku.model.object.Overview;
import com.example.budgetku.model.object.TodayActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static final String SHARED_PREFS = "default";

    private String token;
    private List<DailyActivity> todayActivity;

    RecyclerView rvTodayActivities;
    private TextView usernameText;
    private TextView totalBalance;
    private TextView totalIncome;
    private TextView totalExpense;

    public HomeFragment() {

    }

    public void setUsername(String text)
    {
        usernameText.setText(text);
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        usernameText = (TextView)view.findViewById(R.id.usernameText);
        totalBalance = (TextView)view.findViewById(R.id.total_balance);
        totalIncome = (TextView)view.findViewById(R.id.total_income);
        totalExpense = (TextView)view.findViewById(R.id.total_expense);

        SharedPreferences shared = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        this.token = "Bearer " + shared.getString(LoginActivity.token, "");

        usernameText.setText(shared.getString(username, ""));

        loadOverview();

        rvTodayActivities = (RecyclerView) view.findViewById(R.id.rvTodayActivities);

        loadTodayActivities();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
        rvTodayActivities.setLayoutManager(layoutManager);

        super.onViewCreated(view, savedInstanceState);
    }

    private void loadOverview()
    {
        Call<OverviewResponse> overviewResponseCall = ApiClient.homeService().getOverview(this.token);
        overviewResponseCall.enqueue(new Callback<OverviewResponse>() {
            @Override
            public void onResponse(Call<OverviewResponse> call, Response<OverviewResponse> response) {
                OverviewResponse overviewResponse = response.body();

                if(response.isSuccessful())
                {
                    Overview result = overviewResponse.getData();

                    totalBalance.setText("IDR " + result.getTotal_balance().toString());
                    totalIncome.setText("IDR " + result.getTotal_income().toString());
                    totalExpense.setText("IDR " + result.getTotal_expense().toString());
                }
            }

            @Override
            public void onFailure(Call<OverviewResponse> call, Throwable t) {

            }
        });
    }

    private void loadTodayActivities()
    {
        Call<TodayActivityResponse> activityResponseCall = ApiClient.activityService().getTodayActivities(token);

        activityResponseCall.enqueue(new Callback<TodayActivityResponse>() {
            @Override
            public void onResponse(Call<TodayActivityResponse> call, Response<TodayActivityResponse> response) {
                if(response.isSuccessful())
                {
                    TodayActivityResponse activityResponse= response.body();
                    TodayActivity getToday = activityResponse.getData();

                    DailyActivity today = new DailyActivity();

                    today.setTotal(getToday.getTotal());
                    today.setDate(getToday.getDate());
                    today.setActivities(getToday.getActivities());

                    todayActivity = new ArrayList<>();
                    todayActivity.add(today);

                    DailyActivityAdapter adapter = new DailyActivityAdapter(todayActivity);
                    rvTodayActivities.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<TodayActivityResponse> call, Throwable t) {

            }
        });

    }
}