package com.example.myapplication.data;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserResponse;
import com.example.myapplication.network.ApiService;
import com.example.myapplication.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final UserDao userDao;
    private final LiveData<List<User>> allUsers;
    private final ApiService apiService;

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void delete(User user) {
        UserDatabase.getDatabaseWriteExecutor().execute(() -> userDao.delete(user));
    }

    public void update(User user) {
        UserDatabase.getDatabaseWriteExecutor().execute(() -> userDao.update(user));
    }

    public void insert(User user) {
        UserDatabase.getDatabaseWriteExecutor().execute(() -> userDao.insert(user));
    }

    public void fetchUsersFromApi(int page) {
        apiService.getUsers(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body().getData();
                    UserDatabase.getDatabaseWriteExecutor().execute(() -> {
                        userDao.insertAll(users);
                    });
                    Log.d("UserRepository", "API call successful, users fetched and stored in database.");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("UserRepository", "API call failed: " + t.getMessage());
            }
        });
    }
}
