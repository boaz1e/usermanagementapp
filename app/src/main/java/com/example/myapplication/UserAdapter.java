package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.User;
import com.example.myapplication.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private ApiService apiService;
    private Context context;

    public UserAdapter(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textViewName.setText(user.getFirstName() + " " + user.getLastName());
        holder.textViewEmail.setText(user.getEmail());

        holder.buttonDelete.setOnClickListener(v -> deleteUser(user.getId(), position));
        holder.buttonUpdate.setOnClickListener(v -> showUpdateDialog(user, position));
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public void submitList(List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    private void deleteUser(int userId, int position) {
        Call<Void> call = apiService.deleteUser(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    userList.remove(position);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void showUpdateDialog(User user, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_user, null);
        builder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        editTextName.setText(user.getFirstName() + " " + user.getLastName());

        builder.setPositiveButton("Update", (dialog, which) -> {
            String updatedName = editTextName.getText().toString();
            String[] names = updatedName.split(" ");
            if (names.length >= 2) {
                User updatedUser = new User();
                updatedUser.setId(user.getId());
                updatedUser.setFirstName(names[0]);
                updatedUser.setLastName(names[1]);

                Call<User> call = apiService.updateUser(updatedUser.getId(), updatedUser);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            userList.set(position, response.body());
                            notifyItemChanged(position);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        // Handle failure
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewEmail;
        Button buttonDelete, buttonUpdate;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
        }
    }
}
