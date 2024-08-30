package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.model.User;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {

    private final Context context;
    private final OnUserActionListener onUserActionListener;

    public UserAdapter(Context context, OnUserActionListener onUserActionListener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.onUserActionListener = onUserActionListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        holder.bind(user);
    }

    public interface OnUserActionListener {
        void onDeleteClick(User user);
        void onUpdateClick(User user);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatarImageView;
        private final TextView nameTextView;
        private final TextView emailTextView;
        private final Button deleteButton;
        private final Button updateButton;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.imageViewAvatar);
            nameTextView = itemView.findViewById(R.id.textViewName);
            emailTextView = itemView.findViewById(R.id.textViewEmail);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            updateButton = itemView.findViewById(R.id.buttonUpdate);

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onUserActionListener.onDeleteClick(getItem(position));
                }
            });

            updateButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onUserActionListener.onUpdateClick(getItem(position));
                }
            });
        }

        void bind(User user) {
            nameTextView.setText(user.getFirstName() + " " + user.getLastName());
            emailTextView.setText(user.getEmail());

            // Load the avatar image with Glide
            Glide.with(context)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_placeholder) // Optional placeholder image
                    .error(R.drawable.ic_error) // Optional error image
                    .into(avatarImageView);
        }
    }

    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };
}
