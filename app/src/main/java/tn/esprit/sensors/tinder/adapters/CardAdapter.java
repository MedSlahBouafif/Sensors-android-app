package tn.esprit.sensors.tinder.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.sensors.R;
import tn.esprit.sensors.database.User;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<User> userList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onNoMoreCards();
    }

    public CardAdapter(List<User> userList, OnItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tinder_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUsers(List<User> users) {
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public boolean isCardLiked(int position) {
        // Add logic to check if card is liked
        // Return true if the card at the specified position is liked, false otherwise
        // You need to implement this method based on your requirements
        return false;
    }

    public void dislikeCard(int position) {
        // Add logic to handle card dislike
        if (position >= 0 && position < userList.size()) {
            userList.remove(position);
            notifyItemRemoved(position);
            if (userList.isEmpty()) {
                listener.onNoMoreCards();
            }
        }
    }

    public void likeCard(int position) {
        if (position >= 0 && position < userList.size()) {
            userList.remove(position);
            notifyItemRemoved(position);
            if (userList.isEmpty()) {
                listener.onNoMoreCards();
            }
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        ImageView userImageView;
        TextView nameTextView;
        TextView ageTextView;
        TextView aboutMeTextView;
        LinearLayout centerLayout;

        public LinearLayout getCenterLayout() {
            return centerLayout;
        }

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            userImageView = itemView.findViewById(R.id.userImageView);
            nameTextView = itemView.findViewById(R.id.nameLabelTextView);
            ageTextView = itemView.findViewById(R.id.ageLabelTextView);
            aboutMeTextView = itemView.findViewById(R.id.aboutMeLabelTextView);
            centerLayout = itemView.findViewById(R.id.centerLayout);
        }

        public void bind(User user) {
            // Bind user data to views
            nameTextView.setText("Name: " +user.getName());
            ageTextView.setText("Age: "+String.valueOf(user.getUserAge()));
            aboutMeTextView.setText("About Me: " + user.getUserAboutMe());

            // Set user image if available
            Bitmap userImageBitmap = user.getUserImageBitmap();
            if (userImageBitmap != null) {
                userImageView.setImageBitmap(userImageBitmap);
            } else {
                // Set a placeholder image or handle the absence of an image
                userImageView.setImageResource(R.drawable.placeholder_image);
            }
        }
    }
}