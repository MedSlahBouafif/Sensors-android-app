package tn.esprit.sensors.reservations;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import tn.esprit.sensors.R;
import tn.esprit.sensors.reservations.database.ResDatabase;
import tn.esprit.sensors.reservations.entities.ReservationEntity;

public class UpdateReservationActivity extends AppCompatActivity {

    private ResDatabase database;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView campersCounter;
    private ImageButton incrementButton;
    private ImageButton decrementButton;
    private Button confirmReservationButton;

    private int campersCount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);

        database = Room.databaseBuilder(getApplicationContext(), ResDatabase.class, "reservation_database")
                .build();

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        campersCounter = findViewById(R.id.campersCounter);
        incrementButton = findViewById(R.id.incrementButton);
        decrementButton = findViewById(R.id.decrementButton);
        confirmReservationButton = findViewById(R.id.confirmReservationButton);

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
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                int numberOfCampers = campersCount;


                ReservationEntity reservation = new ReservationEntity();
                reservation.setDay(day);
                reservation.setMonth(month);
                reservation.setYear(year);
                reservation.setHour(hour);
                reservation.setMinute(minute);
                reservation.setNumberOfCampers(numberOfCampers);



                new UpdateReservationTask(database).execute(reservation);
            }
        });
    }

    private void updateCampersCount(int change) {
        campersCount = Math.max(1, campersCount + change);
        campersCounter.setText(getString(R.string.number_of_campers, campersCount));
    }

    private static class UpdateReservationTask extends AsyncTask<ReservationEntity, Void, Void> {
        private ResDatabase database;

        UpdateReservationTask(ResDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(ReservationEntity... reservations) {
            database.reservationDao().update(reservations[0]);
            return null;
        }
    }
}
