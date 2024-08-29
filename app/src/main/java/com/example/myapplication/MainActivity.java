package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        UserAdapter adapter = new UserAdapter(this);
        recyclerView.setAdapter(adapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, adapter::submitList);

        userViewModel.getAllUsers().observe(this, users -> {
            if (users != null && !users.isEmpty()) {
                Log.d("MainActivity", "Users received: " + users.size());
                adapter.submitList(users);
            } else {
                Log.e("MainActivity", "No users received or users list is empty");
            }
        });

        Log.d("MainActivity", "RecyclerView and Adapter initialized");
    }
}
