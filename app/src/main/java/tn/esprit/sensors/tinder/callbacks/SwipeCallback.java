package tn.esprit.sensors.tinder.callbacks;

import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import tn.esprit.sensors.tinder.TinderActivity;
import tn.esprit.sensors.tinder.adapters.CardAdapter;

public class SwipeCallback extends ItemTouchHelper.Callback {

    private CardAdapter cardAdapter;
    private TinderActivity tinderActivity;
    private RecyclerView recyclerView;

    public SwipeCallback(CardAdapter cardAdapter, TinderActivity tinderActivity, RecyclerView recyclerView) {
        this.cardAdapter = cardAdapter;
        this.tinderActivity = tinderActivity;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        final View foregroundView = ((CardAdapter.CardViewHolder) viewHolder).getCenterLayout();

        switch (direction) {
            case ItemTouchHelper.LEFT:
                cardAdapter.dislikeCard(position);
                break;
            case ItemTouchHelper.RIGHT:
                cardAdapter.likeCard(position);
                break;
        }

        // Check if there are more cards and reset the adapter position
        if (position == cardAdapter.getItemCount() - 1) {
            recyclerView.smoothScrollToPosition(0);
        }

        if (cardAdapter.getItemCount() == 0) {
            tinderActivity.onNoMoreCards();
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((CardAdapter.CardViewHolder) viewHolder).getCenterLayout();

            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((CardAdapter.CardViewHolder) viewHolder).getCenterLayout();

        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((CardAdapter.CardViewHolder) viewHolder).getCenterLayout();

        getDefaultUIUtil().clearView(foregroundView);
    }

}
