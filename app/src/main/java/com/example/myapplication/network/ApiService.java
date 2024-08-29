package com.example.myapplication.network;

import com.example.myapplication.model.User;
import com.example.myapplication.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/users")
    Call<UserResponse> getUsers(@Query("page") int page);

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @PUT("api/users/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);
}
