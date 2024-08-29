package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.model.User;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserHolder> {

    private OnItemClickListener listener;

    public UserAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getFirstName().equals(newItem.getFirstName()) &&
                    oldItem.getLastName().equals(newItem.getLastName()) &&
                    oldItem.getEmail().equals(newItem.getEmail()) &&
                    oldItem.getAvatar().equals(newItem.getAvatar());
        }
    };

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User currentUser = getItem(position);
        if (currentUser != null) {
            holder.textViewName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            holder.textViewEmail.setText(currentUser.getEmail());
            Glide.with(holder.imageViewAvatar.getContext()).load(currentUser.getAvatar()).into(holder.imageViewAvatar);
        }
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewEmail;
        private ImageView imageViewAvatar;

        public UserHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
