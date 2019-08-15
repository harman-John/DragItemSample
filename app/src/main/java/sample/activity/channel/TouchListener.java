package sample.activity.channel;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.logging.Logger;


public class TouchListener extends ItemTouchHelper.Callback {

    private static final String TAG = TouchListener.class.getSimpleName();
    private IItemHelper itemHelper;

    public TouchListener(IItemHelper itemHelper) {

        this.itemHelper = itemHelper;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        itemHelper.itemMoved(viewHolder.getLayoutPosition(), target.getLayoutPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemHelper.itemDismiss(viewHolder.getLayoutPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }


    @Override
    public boolean isItemViewSwipeEnabled() {

        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        Log.d(TAG, "onChildDraw actionState = " + actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
        } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
        } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
        }
    }
}
