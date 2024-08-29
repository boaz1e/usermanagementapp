package com.example.myapplication.data;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserResponse;
import com.example.myapplication.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class UserRepository {
    private ApiService apiService;
    private MutableLiveData<List<User>> users;

    public UserRepository(Application application) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        users = new MutableLiveData<>();
    }

    public LiveData<List<User>> getUsers(int page) {
        apiService.getUsers(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("UserRepository", "API call successful, data received: " + response.body().getData().size() + " users");
                    users.setValue(response.body().getData());
                } else {
                    Log.e("UserRepository", "API call failed or response body is null");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("UserRepository", "API call failed: " + t.getMessage());
            }
        });

        return users;
    }
}
