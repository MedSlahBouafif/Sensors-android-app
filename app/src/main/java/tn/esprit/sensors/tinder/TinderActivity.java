package tn.esprit.sensors.tinder;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import tn.esprit.sensors.R;
import tn.esprit.sensors.database.User;
import tn.esprit.sensors.database.UserDatabase;
import tn.esprit.sensors.tinder.adapters.CardAdapter;
import tn.esprit.sensors.tinder.callbacks.SwipeCallback;

import java.util.ArrayList;
import java.util.List;

public class TinderActivity extends AppCompatActivity {

    private CardAdapter cardAdapter;
    private RecyclerView recyclerView;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder);

        recyclerView = findViewById(R.id.recyclerView);
        setupRecyclerView();

        // Initial load of users from the database
        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        new Thread(() -> {
            UserDatabase userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "UserDatabase").build();
            userList = userDatabase.getUserDao().getAllUsers();

            runOnUiThread(() -> {
                if (userList.isEmpty()) {
                    showNoMoreCardsMessage();
                } else {
                    cardAdapter.addUsers(userList);
                }
            });
        }).start();
    }
    private void showNoMoreCardsMessage() {
        Toast.makeText(this, "No more cards available. Reload more cards!", Toast.LENGTH_SHORT).show();
        // You can implement additional logic here, such as loading more cards
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(new ArrayList<>(), new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!cardAdapter.isCardLiked(position)) {
                    showUserDetails(cardAdapter.getUserList().get(position));
                } else {
                    Toast.makeText(TinderActivity.this, "Already liked!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNoMoreCards() {
                // Your implementation when there are no more cards
                // For example, show a message or take any other action
            }
        });
        recyclerView.setAdapter(cardAdapter);

        SwipeCallback swipeCallback = new SwipeCallback(cardAdapter, this, recyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);  // Set the RecyclerView here
    }

    private void showUserDetails(User user) {
        // Your implementation to show user details
        // For example, open a new activity or fragment with user details
    }

    public void onNoMoreCards() {

    }
}