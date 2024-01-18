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
import tn.esprit.sensors.reservations.entities.PopularItem;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private Context context;
    private List<PopularItem> popularList;
    private OnItemClickListener onItemClickListener;

    public PopularAdapter(Context context, List<PopularItem> popularList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.popularList = popularList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popular, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        PopularItem popularItem = popularList.get(position);

        // Customize the binding of data to views here
        holder.imageView.setImageResource(popularItem.getPopularImageId());
        holder.textView.setText(popularItem.getPopularItemName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAbsoluteAdapterPosition();
                if (onItemClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    // Pass data to DetailsActivity when an item is clicked
                    PopularItem clickedItem = popularList.get(clickedPosition);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("itemName", clickedItem.getPopularItemName());
                    intent.putExtra("imageId", clickedItem.getPopularImageId());
                    intent.putExtra("description", clickedItem.getPopularAdditionalData());
                    intent.putExtra("location", clickedItem.getPopularLocation());
                    intent.putExtra("phone", clickedItem.getPopularPhone());
                    intent.putExtra("time", clickedItem.getPopulartime());
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
        return popularList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPopular);
            textView = itemView.findViewById(R.id.textViewPop);
        }

    }

    public PopularItem getItem(int position) {
        if (position >= 0 && position < popularList.size()) {
            return popularList.get(position);
        }
        return null;
    }

}
