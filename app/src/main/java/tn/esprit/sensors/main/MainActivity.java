package tn.esprit.sensors.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.sensors.R;
import tn.esprit.sensors.sites.CampsiteFragment;

public class MainActivity extends AppCompatActivity implements SnapAdapter.OnItemClickListener {

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView2 = findViewById(R.id.recyclerView2);

        // Set up RecyclerView1
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);

        // Set up RecyclerView2
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);

        // Sample data for RecyclerView1
        List<Snap> data1 = new ArrayList<>();
        data1.add(new Snap("Your Item 1", R.drawable.fb, "Additional Data for Item 1"));
        data1.add(new Snap("Your Item 2", R.drawable.twitter, "Additional Data for Item 2"));
        data1.add(new Snap("Your Item 3", R.drawable.google, "Additional Data for Item 3"));

        // Sample data for RecyclerView2
        List<Snap> data2 = new ArrayList<>();
        data2.add(new Snap("Another Item A", R.drawable.fb, "Additional Data for Another Item A"));
        data2.add(new Snap("Another Item B", R.drawable.twitter, "Additional Data for Another Item B"));
        data2.add(new Snap("Another Item C", R.drawable.google, "Additional Data for Another Item C"));

        // Assuming you have a SnapAdapter for your items
        SnapAdapter adapter1 = new SnapAdapter(this, data1, this);
        SnapAdapter adapter2 = new SnapAdapter(this, data2, this);

        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    public void onItemClick(int position) {
        List<Snap> data1 = new ArrayList<>();
        data1.add(new Snap("Your Item 1", R.drawable.fb, "Additional Data for Item 1"));
        data1.add(new Snap("Your Item 2", R.drawable.twitter, "Additional Data for Item 2"));
        data1.add(new Snap("Your Item 3", R.drawable.google, "Additional Data for Item 3"));

        String imageName = data1.get(position).getItemName();
        int imageId = data1.get(position).getImageId();
        String additionalData = data1.get(position).getAdditionalData();

        CampsiteFragment fragment = CampsiteFragment.newInstance(imageId, imageName, additionalData);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();

        findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
    }

    public void setVisible(boolean visible) {
        View fragmentContainer = findViewById(R.id.fragmentContainer);
        if (fragmentContainer != null) {
            if (visible) {
                fragmentContainer.setVisibility(View.VISIBLE);
            } else {
                fragmentContainer.setVisibility(View.INVISIBLE);
            }
        }
    }
}
