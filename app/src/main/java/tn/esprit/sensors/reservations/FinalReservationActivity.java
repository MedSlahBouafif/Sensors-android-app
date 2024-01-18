package tn.esprit.sensors.reservations;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import tn.esprit.sensors.R;
import tn.esprit.sensors.reservations.database.ResDatabase;
import tn.esprit.sensors.reservations.entities.ReservationEntity;

import java.util.HashSet;
import java.util.Set;

public class FinalReservationActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView campersCounter;
    private ImageButton incrementButton;
    private ImageButton decrementButton;
    private Button confirmReservationButton;
    private ImageView toolbarImageView;

    public static ResDatabase database;
    private int campersCount = 1; // Variable to track campers count
    private Set<String> selectedItems = new HashSet<>(); // Set to store selected items

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_reservation);

        database = Room.databaseBuilder(getApplicationContext(), ResDatabase.class, "reservation_database")
                .build();

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        campersCounter = findViewById(R.id.campersCounter);
        incrementButton = findViewById(R.id.incrementButton);
        decrementButton = findViewById(R.id.decrementButton);
        confirmReservationButton = findViewById(R.id.confirmReservationButton);
        toolbarImageView = findViewById(R.id.toolbarImageView);

        // Set initial campers count
        campersCounter.setText(getString(R.string.number_of_campers, campersCount));

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCampersCount(1);
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCampersCount(-1);
            }
        });

        confirmReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                int numberOfCampers = campersCount; // Use the tracked variable

                ReservationEntity reservation = new ReservationEntity();
                reservation.setDay(day);
                reservation.setMonth(month);
                reservation.setYear(year);
                reservation.setHour(hour);
                reservation.setMinute(minute);
                reservation.setNumberOfCampers(numberOfCampers);

                // Concatenate selected items into details with "-"
                StringBuilder detailsBuilder = new StringBuilder();
                for (String item : selectedItems) {
                    detailsBuilder.append(item).append("-"); // Add "-" between items
                }
                reservation.setDetails(detailsBuilder.toString());

                new InsertReservationTask().execute(reservation);
            }
        });

        toolbarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FinalReservationActivity.this, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.toolbar_menu, popupMenu.getMenu());

                AlertDialog.Builder builder = new AlertDialog.Builder(FinalReservationActivity.this);
                builder.setTitle("Select Items");

                final CharSequence[] items = new CharSequence[popupMenu.getMenu().size()];
                final boolean[] checkedItems = new boolean[popupMenu.getMenu().size()];

                for (int i = 0; i < popupMenu.getMenu().size(); i++) {
                    items[i] = popupMenu.getMenu().getItem(i).getTitle();
                    checkedItems[i] = selectedItems.contains(items[i].toString());
                }

                builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String itemName = items[which].toString();
                        if (isChecked) {
                            // Add item to selectedItems if it's not selected
                            selectedItems.add(itemName);
                        } else {
                            // Remove item from selectedItems if it's already selected
                            selectedItems.remove(itemName);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle OK button click if needed
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle Cancel button click if needed
                    }
                });

                builder.show();
            }
        });



    }

    private void updateCampersCount(int change) {
        campersCount = Math.max(1, campersCount + change);
        campersCounter.setText(getString(R.string.number_of_campers, campersCount));
    }

    private void handleMenuItemClick(MenuItem item) {
        String itemName = item.getTitle().toString();

        if (selectedItems.contains(itemName)) {
            // Remove item from selectedItems if it's already selected
            selectedItems.remove(itemName);
            item.setChecked(false);
        } else {
            // Add item to selectedItems if it's not selected
            selectedItems.add(itemName);
            item.setChecked(true);
        }
    }

    private static class InsertReservationTask extends AsyncTask<ReservationEntity, Void, Void> {
        @Override
        protected Void doInBackground(ReservationEntity... reservations) {
            database.reservationDao().insert(reservations[0]);
            return null;
        }
    }
}
