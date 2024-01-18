package tn.esprit.sensors.reservations.adapters;

// Import necessary classes
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.sensors.R;
import tn.esprit.sensors.reservations.DetailsActivity;
import tn.esprit.sensors.reservations.entities.RecommendedItem;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<RecommendedItem> recommendedList;
    private OnItemClickListener onItemClickListener;

    public RecommendedAdapter(Context context, List<RecommendedItem> recommendedList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.recommendedList = recommendedList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommended, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        RecommendedItem recommendedItem = recommendedList.get(position);

        // Customize the binding of data to views here
        holder.imageView.setImageResource(recommendedItem.getRecommendedImageId());
        holder.textView.setText(recommendedItem.getRecommendedItemName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAbsoluteAdapterPosition();
                if (onItemClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    // Pass data to DetailsActivity when an item is clicked
                    RecommendedItem clickedItem = recommendedList.get(clickedPosition);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("itemName", clickedItem.getRecommendedItemName());
                    intent.putExtra("imageId", clickedItem.getRecommendedImageId());
                    intent.putExtra("description", clickedItem.getRecommendedAdditionalData());
                    intent.putExtra("locatiob", clickedItem.getRecloc());
                    intent.putExtra("phone", clickedItem.getRecph());
                    intent.putExtra("time", clickedItem.getRect());
                    intent.putExtra("temp",clickedItem.getTemp());
                    intent.putExtra("press",clickedItem.getPress());
                    intent.putExtra("hum",clickedItem.getHum());


                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewRec);
            textView = itemView.findViewById(R.id.textViewRec);
        }

    }

    public RecommendedItem getItem(int position) {
        if (position >= 0 && position < recommendedList.size()) {
            return recommendedList.get(position);
        }
        return null;
    }
}
