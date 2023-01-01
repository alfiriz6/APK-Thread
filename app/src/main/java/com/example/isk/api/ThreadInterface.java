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

public interface ThreadInterface {

    @GET("thread")
    Call<ThreadResponse> getThread();

    @FormUrlEncoded
    @POST("thread")
    Call<Thread> postThread(@Field("judul") String judul,
                            @Field("gambar") String gambar,
                            @Field("isi") String isi);

    @Headers("Content-Type: application/json")
    @PUT("thread/{id}")
    Call<Thread> updateThread(@Path("id") int id,
                              @Body Thread body);

    @DELETE("thread/{id}")
    Call<Thread> deleteThread(@Path("id") int id);
}
