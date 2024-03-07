package com.example.retrofitapp;

import com.example.retrofitapp.ModelResponse.DeleteResponse;
import com.example.retrofitapp.ModelResponse.FetchUserResponse;
import com.example.retrofitapp.ModelResponse.LoginResponse;
import com.example.retrofitapp.ModelResponse.RegisterResponse;
import com.example.retrofitapp.ModelResponse.UpdatePassResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    // SignUp API
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    // login API
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    // Fetch User AP
    @GET("fetchusers.php")
    Call<FetchUserResponse> fetchAllUsers();

    // UPDATE User ACCOUNT API
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUserAccount(
            @Field("id") int userId,
            @Field("username") String username,
            @Field("email") String email
    );

    // UPDATE PASSWORD API
    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdatePassResponse> updateUserPassword(
            @Field("email") String email,
            @Field("current") String currentPassword,
            @Field("new") String newPassword
    );

    // DELETE USER ACCOUNT API
    @FormUrlEncoded
    @POST("deleteaccount.php")
    Call<DeleteResponse> deleteUserAccount(
            @Field("id") int userId
    );

}
