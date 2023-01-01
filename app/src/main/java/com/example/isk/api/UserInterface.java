package com.example.isk.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    @GET("user")
    Call<UserResponse> getUser();

    @FormUrlEncoded
    @POST("register")
    Call<User> postUser(@Field("username") String username,
                        @Field("nama") String nama,
                        @Field("email") String email,
                        @Field("password") String password,
                        @Field("kpassword") String kpassword);

    @Headers("Content-Type: application/json")
    @PUT("user/{id}")
    Call<User> updateUser(@Path("id") int id,
                          @Body User body);

    @DELETE("user/{id}")
    Call<User> deleteUser(@Path("id") int id);
}
