package tn.esprit.sensors.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.esprit.sensors.R;

public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.SnapViewHolder> {

    private Context context;
    private List<Snap> snapList;
    private OnItemClickListener onItemClickListener;

    public SnapAdapter(Context context, List<Snap> snapList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.snapList = snapList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SnapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowview_snap, parent, false);
        return new SnapViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SnapViewHolder holder, final int position) {
        Snap snap = snapList.get(position);
        holder.imageView.setImageResource(snap.getImageId());
        holder.textView.setText(snap.getItemName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAbsoluteAdapterPosition();
                if (onItemClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(clickedPosition);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return snapList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class SnapViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public SnapViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
