package tn.esprit.sensors.reservations.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.sensors.R;
import tn.esprit.sensors.reservations.entities.ReservationEntity;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<ReservationEntity> reservations;
    private OnItemClickListener listener;

    public ReservationAdapter(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public interface OnItemClickListener {
        void onUpdateClick(ReservationEntity reservation);
        void onDeleteClick(ReservationEntity reservation);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        if (position < 0 || position >= reservations.size()) {
            return; // Add error handling here if needed
        }

        ReservationEntity reservation = reservations.get(position);
        holder.bind(reservation);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void removeReservation(ReservationEntity reservation) {
        reservations.remove(reservation);
        notifyDataSetChanged();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder {

        private TextView detailsTextView;
        private Button updateButton;
        private Button deleteButton;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);

            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (isValidPosition(position)) {
                        listener.onUpdateClick(reservations.get(position));
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (isValidPosition(position)) {
                        listener.onDeleteClick(reservations.get(position));
                    }
                }
            });
        }

        public void bind(ReservationEntity reservation) {
            String details = "ID: " + reservation.getId() +
                    "\nDate: " + reservation.getDay() + "/" + reservation.getMonth() + "/" + reservation.getYear() +
                    "\nTime: " + reservation.getHour() + ":" + reservation.getMinute() +
                    "\nCampers: " + reservation.getNumberOfCampers() +
                    "\nDetails: " + reservation.getDetails();


            detailsTextView.setText(details);
        }

        private boolean isValidPosition(int position) {
            return position != RecyclerView.NO_POSITION && listener != null;
        }
    }

}
