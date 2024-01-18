package tn.esprit.sensors.tinder.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.sensors.R;
import tn.esprit.sensors.tinder.entites.Match;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {
    private List<Match> matches;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Match match);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public MatchAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.textName.setText(match.getName());
        holder.textAge.setText(String.valueOf(match.getAge()));
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textAge;

        MatchViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textAge = itemView.findViewById(R.id.textAge);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.textName.setText(match.getName());
        holder.textAge.setText(String.valueOf(match.getAge()));

        // Set click listener for the item
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(match);
            }
        });
    }
}