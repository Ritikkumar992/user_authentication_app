package com.example.retrofitapp.ModelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchUserResponse {
    List<User> users;
    String status;

    public FetchUserResponse(List<User> userList, String status) {
        this.users = userList;
        this.status = status;
    }

    public List<User> getUserList() {
        return users;
    }

    public void setUserList(List<User> userList) {
        this.users = userList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
