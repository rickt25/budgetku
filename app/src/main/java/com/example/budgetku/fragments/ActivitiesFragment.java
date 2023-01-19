package com.example.budgetku.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.budgetku.Api.ApiClient;
import com.example.budgetku.R;
import com.example.budgetku.activities.AddActivity;
import com.example.budgetku.activities.AddWalletActivity;
import com.example.budgetku.activities.LoginActivity;
import com.example.budgetku.adapters.DailyActivityAdapter;
import com.example.budgetku.model.api.ActivityResponse;
import com.example.budgetku.model.object.DailyActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitiesFragment extends Fragment {

    public static final String SHARED_PREFS = "default";
    private String token;

    private List<DailyActivity> dailyActivityList;

    private ImageButton addActivityButton;
    RecyclerView rvAllActivities;

    public static ActivitiesFragment newInstance(String param1, String param2) {
        ActivitiesFragment fragment = new ActivitiesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences shared = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        this.token = "Bearer " + shared.getString(LoginActivity.token,"");


        addActivityButton = (ImageButton) view.findViewById(R.id.addActivityButton);
        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivityForResult(intent, 10001);
            }
        });

        rvAllActivities = (RecyclerView) view.findViewById(R.id.rvAllActivities);

        loadData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
        rvAllActivities.setLayoutManager(layoutManager);

        super.onViewCreated(view, savedInstanceState);
    }

    private void loadData()
    {
        Call<ActivityResponse> activityResponseCall = ApiClient.activityService().getAllActivities(token);
        activityResponseCall.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {

                if(response.isSuccessful())
                {
                    ActivityResponse activityResponse = response.body();
                    dailyActivityList = activityResponse.getData();

                    DailyActivityAdapter adapter = new DailyActivityAdapter(dailyActivityList);
                    rvAllActivities.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        FragmentManager fragmentManager = getFragmentManager();
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK))
            // recreate your fragment here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fragmentManager.beginTransaction().detach(this).commitNow();
                fragmentManager.beginTransaction().attach(this).commitNow();
            } else {
                fragmentManager.beginTransaction().detach(this).attach(this).commit();
            }
    }
}