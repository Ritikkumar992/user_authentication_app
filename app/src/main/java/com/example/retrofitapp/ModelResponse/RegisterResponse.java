package com.example.retrofitapp.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    String status;
    String message;

    public RegisterResponse(String status, String msg) {
        this.status = status;
        this.message = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

//{
//        "success": "200",
//        "message": "Registration successful!"
//        }
