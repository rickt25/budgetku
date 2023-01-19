package com.example.budgetku.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetku.Api.ApiClient;
import com.example.budgetku.R;
import com.example.budgetku.activities.AddWalletActivity;
import com.example.budgetku.activities.LoginActivity;
import com.example.budgetku.adapters.WalletAdapter;
import com.example.budgetku.model.api.WalletResponse;
import com.example.budgetku.model.object.Wallet;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletsFragment extends Fragment {

    public static final String SHARED_PREFS = "default";
    private RecyclerView rvWallets;
    private TextView totalWealthView;
    private ImageButton addButton;
    private WalletAdapter adapter;
    private Integer totalWealth = 0;
    private String token;


    public WalletsFragment() {
        // Required empty public constructor
    }

    public static WalletsFragment newInstance(String param1, String param2) {
        WalletsFragment fragment = new WalletsFragment();

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
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addButton = (ImageButton) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddWalletActivity.class);
                startActivityForResult(intent, 10001);
            }
        });

        SharedPreferences shared = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        this.token = "Bearer " + shared.getString(LoginActivity.token,"");

        totalWealthView = (TextView)getView().findViewById(R.id.totalWealthText);

        rvWallets = (RecyclerView)getView().findViewById(R.id.walletList);
        adapter = new WalletAdapter(token);
        loadWallets();
        rvWallets.setLayoutManager(new LinearLayoutManager(getView().getContext()));

        super.onViewCreated(view, savedInstanceState);
    }

    public void loadWallets(){
        Call<WalletResponse> walletResponseCall = ApiClient.walletService().getWalletById(token);

        walletResponseCall.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                WalletResponse walletResponse = response.body();

                if(response.isSuccessful())
                {
                    if(!walletResponse.getData().isEmpty())
                    {
                        totalWealth = new Integer(0);
                        for (Wallet wallet: walletResponse.getData()) {
                            totalWealth += wallet.getBalance();
                        }

                        totalWealthView.setText(currencyRupiah(totalWealth.toString()));
                        adapter.setWallets(walletResponse.getData());
                        rvWallets.setAdapter(adapter);
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                Toast.makeText(getContext(), "throwable" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

    private String currencyRupiah(String amount){
        Double money = Double.parseDouble(amount);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(money);
    }
}