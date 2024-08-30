package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.viewmodel.UserViewModel;
import com.example.myapplication.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements UserAdapter.OnUserActionListener {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        UserAdapter adapter = new UserAdapter(this, this);
        recyclerView.setAdapter(adapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
            if (users != null && !users.isEmpty()) {
                Log.d("MainActivity", "Users received: " + users.size());
            } else {
                Log.e("MainActivity", "No users received or users list is empty");
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showAddUserDialog());

        Log.d("MainActivity", "RecyclerView and Adapter initialized");
    }

    public void onDeleteClick(User user) {
        userViewModel.delete(user);
    }

    public void onUpdateClick(User user) {
        showUpdateUserDialog(user);
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        Button buttonAdd = dialogView.findViewById(R.id.buttonUpdate);

        AlertDialog dialog = builder.create();

        buttonAdd.setText("Add");
        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (!name.isEmpty()) {
                String[] nameParts = name.split(" ");
                String firstName = nameParts[0];
                String lastName = nameParts.length > 1 ? nameParts[1] : "";

                User newUser = new User(firstName, lastName, "newuser@example.com", "https://example.com/avatar.png");
                userViewModel.insert(newUser);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showUpdateUserDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);

        editTextName.setText(user.getFirstName() + " " + user.getLastName());

        AlertDialog dialog = builder.create();

        buttonUpdate.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (!name.isEmpty()) {
                String[] nameParts = name.split(" ");
                user.setFirstName(nameParts[0]);
                user.setLastName(nameParts.length > 1 ? nameParts[1] : "");
                userViewModel.update(user);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
