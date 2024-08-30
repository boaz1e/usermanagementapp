package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.myapplication.model.User;
import com.example.myapplication.data.UserRepository;
import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository repository;
    private final LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void insert(User user) {
        repository.insert(user);
    }
}
