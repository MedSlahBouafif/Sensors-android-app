package tn.esprit.sensors.reservations;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.sensors.R;
import tn.esprit.sensors.reservations.adapters.ReservationAdapter;
import tn.esprit.sensors.reservations.database.ResDatabase;
import tn.esprit.sensors.reservations.entities.ReservationEntity;
public class CheckReclamationsActivity extends AppCompatActivity {
    private ResDatabase database;
    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reclamations);

        database = ResDatabase.getInstance(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Execute the database operation in a background thread
        new LoadReservationsTask().execute();
    }

    private class LoadReservationsTask extends AsyncTask<Void, Void, List<ReservationEntity>> {
        @Override
        protected List<ReservationEntity> doInBackground(Void... voids) {
            return database.reservationDao().getAllReservations();
        }

        @Override
        protected void onPostExecute(List<ReservationEntity> reservations) {
            super.onPostExecute(reservations);

            // Initialize the adapter and set it to the RecyclerView
            reservationAdapter = new ReservationAdapter(reservations);
            recyclerView.setAdapter(reservationAdapter);

            // Set up click listeners for each item
            reservationAdapter.setOnItemClickListener(new ReservationAdapter.OnItemClickListener() {
                @Override
                public void onUpdateClick(ReservationEntity reservation) {
                    updateReservation(reservation);
                }

                @Override
                public void onDeleteClick(ReservationEntity reservation) {
                    deleteReservation(reservation);
                }
            });
        }
    }

    private void updateReservation(ReservationEntity reservation) {
        Intent intent = new Intent(this, UpdateReservationActivity.class);
        intent.putExtra("reservation_id", reservation.getId());
        startActivityForResult(intent, 1);
    }

    private void deleteReservation(ReservationEntity reservation) {
        new DeleteReservationTask(database).execute(reservation);
        reservationAdapter.removeReservation(reservation);
    }

    private class DeleteReservationTask extends AsyncTask<ReservationEntity, Void, Void> {
        private ResDatabase database;

        DeleteReservationTask(ResDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(ReservationEntity... reservations) {
            database.reservationDao().delete(reservations[0]);
            return null;
        }
    }
}
