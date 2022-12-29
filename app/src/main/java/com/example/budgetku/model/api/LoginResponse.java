package com.example.budgetku.model.api;

import com.example.budgetku.model.object.Response;
import com.example.budgetku.model.object.User;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String token;
    private User user;
    private Boolean status;
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
