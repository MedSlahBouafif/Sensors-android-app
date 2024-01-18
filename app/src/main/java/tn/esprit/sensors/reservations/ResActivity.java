package tn.esprit.sensors.reservations;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.sensors.R;
import tn.esprit.sensors.reservations.adapters.PopularAdapter;
import tn.esprit.sensors.reservations.adapters.RecommendedAdapter;
import tn.esprit.sensors.reservations.entities.PopularItem;
import tn.esprit.sensors.reservations.entities.RecommendedItem;

public class ResActivity extends AppCompatActivity
        implements PopularAdapter.OnItemClickListener, RecommendedAdapter.OnItemClickListener {

    private RecyclerView recyclerViewPopular;
    private RecyclerView recyclerViewRecommended;
    private Button checkReclamationsButton;

    private PopularAdapter popularAdapter;
    private UtilsActivity utilsActivity;
    private RecommendedAdapter recommendedAdapter;

    private List<PopularItem> popularData;
    private List<RecommendedItem> recommendedData;

    private FloatingActionButton openDrawerButton;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);

        recyclerViewPopular = findViewById(R.id.recyclerViewPopular);
        recyclerViewRecommended = findViewById(R.id.recyclerViewRecommended);

        openDrawerButton = findViewById(R.id.openDrawerButton);
        drawerLayout = findViewById(R.id.drawerLayout);

        // Set up RecyclerView for Popular
        LinearLayoutManager layoutManagerPopular = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);

        // Set up RecyclerView for Recommended
        LinearLayoutManager layoutManagerRecommended = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRecommended.setLayoutManager(layoutManagerRecommended);

        // Sample data for Popular
        popularData = new ArrayList<>();
        popularData.add(new PopularItem("Popular Item 1", R.drawable.site1, "sza bla", "LOCATION", "22757788", "Time", "832", "13", "124"));
        popularData.add(new PopularItem("Popular Item ", R.drawable.site10, "sza bla", "LOCATION", "22757788", "TIME", "3213", "13", "124"));
        popularData.add(new PopularItem("Popular Item 2", R.drawable.site18, "sza bla", "LOCATION", "22757788", "TIME", "321", "13", "124"));

        // Sample data for Recommended
        recommendedData = new ArrayList<>();
        recommendedData.add(new RecommendedItem("Recommended Item 1", R.drawable.site7, "bla bla", "unbi", "733", "uhia", "25", "13", "124"));
        recommendedData.add(new RecommendedItem("Recommended Item 13", R.drawable.site10, "bla bla", "unbi", "733", "uhia", "25", "773", "124"));
        recommendedData.add(new RecommendedItem("Recommended Item 4", R.drawable.site1, "bla bla", "unbi", "733", "uhia", "423", "13", "124"));

        // Create adapters for Categories, Popular, and Recommended
        popularAdapter = new PopularAdapter(this, popularData, this);
        recommendedAdapter = new RecommendedAdapter(this, recommendedData, this);

        // Set adapters to RecyclerViews
        recyclerViewPopular.setAdapter(popularAdapter);
        recyclerViewRecommended.setAdapter(recommendedAdapter);

        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle item click here
            int itemId = item.getItemId();
            if (itemId == R.id.menu_1) {
                Intent checkReclamationsIntent = new Intent(ResActivity.this, CheckReclamationsActivity.class);
                startActivity(checkReclamationsIntent);
            } else if (itemId == R.id.menu_2) {
              Intent finalReservationsIntent = new Intent(ResActivity.this, FinalReservationActivity.class);
                startActivity(finalReservationsIntent);
            } else if (itemId == R.id.menu_3) {
                Intent utilsActivity = new Intent(ResActivity.this, UtilsActivity.class);
                startActivity(utilsActivity);
            }

            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });



        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setButtonImage(int drawableId, int backgroundColorId) {
        try {
            ImageView menuImageView = findViewById(R.id.menuImageView);
            Drawable drawable = getResources().getDrawable(drawableId); // Load the drawable from resources
            int backgroundColor = getResources().getColor(backgroundColorId);

            // Set the image resource
            menuImageView.setImageResource(drawableId);

            // Tint the image with the background color
            drawable.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN);
            menuImageView.setImageDrawable(drawable);
        } catch (Exception e) {
            Log.e("ResActivity", "Error setting button image", e);
        }
    }



    // Method to handle button clicks
    public void onMenuItemClick(View view) {
        // Handle button click if needed
        Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        // Handle item click...
    }
}
