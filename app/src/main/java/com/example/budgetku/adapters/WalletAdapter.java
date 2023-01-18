package com.example.budgetku.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetku.Api.ApiClient;
import com.example.budgetku.R;
import com.example.budgetku.activities.LoginActivity;
import com.example.budgetku.model.api.WalletResponse;
import com.example.budgetku.model.object.Response;
import com.example.budgetku.model.object.Wallet;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder>{

    private String token;
    private AlertDialog.Builder dialog;
    private AlertDialog alertDialog;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView wallet_name;
        public TextView balance;
        public ImageButton deleteButton;

        public ViewHolder(View itemView) {

            super(itemView);

            wallet_name = (TextView) itemView.findViewById(R.id.wallet_name);
            balance = (TextView) itemView.findViewById(R.id.balance);
            deleteButton = (ImageButton) itemView.findViewById(R.id.delete_wallet_button);
        }
    }

    private List<Wallet> wallets;

    public WalletAdapter (String token)
    {
        this.token = token;
    }

    public void setWallets(List<Wallet> wallets)
    {
        this.wallets = wallets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View walletView = inflater.inflate(R.layout.item_wallet, parent, false);

        ViewHolder viewHolder = new ViewHolder(walletView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder holder, int position) {
        Wallet wallet = wallets.get(position);

        TextView walletName = holder.wallet_name;
        walletName.setText(wallet.getWallet_name());

        TextView balance = holder.balance;
        balance.setText("IDR" + wallet.getBalance().toString());

        ImageButton deleteButton = holder.deleteButton;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWallet(wallet);
            }
        });

        dialog = new AlertDialog.Builder(holder.itemView.getContext());
        dialog.setMessage("Wallet Deleted");
        dialog.setTitle("Notification");
        alertDialog = dialog.create();

    }

    public void deleteWallet(Wallet wallet){
        Call<com.example.budgetku.model.object.Response> deleteResponse = ApiClient.walletService().deleteWalletById(token, wallet.getId().toString());

        deleteResponse.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful())
                {
                    wallets.remove(wallet);
                    notifyDataSetChanged();

                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.wallets.size();
    }
}
