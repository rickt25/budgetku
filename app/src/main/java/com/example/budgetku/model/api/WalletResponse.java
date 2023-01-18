package com.example.budgetku.model.api;

import com.example.budgetku.model.object.Response;
import com.example.budgetku.model.object.Wallet;

import java.util.List;

public class WalletResponse extends Response {

    private List<Wallet> data;

    public List<Wallet> getData() {
        return data;
    }

    public void setData(List<Wallet> data) {
        this.data = data;
    }


}
