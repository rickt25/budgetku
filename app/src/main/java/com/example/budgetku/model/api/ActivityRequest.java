package com.example.budgetku.model.api;

public class ActivityRequest {
    private Integer category_id;
    private Integer activity_type;
    private Integer amount;
    private Integer wallet_id;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(Integer activity_type) {
        this.activity_type = activity_type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(Integer wallet_id) {
        this.wallet_id = wallet_id;
    }
}
