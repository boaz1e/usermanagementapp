package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.myapplication.data.UserRepository;
import com.example.myapplication.model.User;
import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        users = repository.getUsers(2); // Fetch users from page 2 as an example
    }

    // This is the method you're trying to call in MainActivity
    public LiveData<List<User>> getAllUsers() {
        return users;
    }
}
