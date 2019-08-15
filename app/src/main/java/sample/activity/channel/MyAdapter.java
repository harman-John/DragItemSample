package sample.activity.channel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sample.activity.R;


public class MyAdapter extends RecyclerView.Adapter<ViewHolder> implements IItemHelper {
    public static final int MY_TITLE = 1;
    private static final int MY_PRESET = 2;
    private static final int MY_CUSTOM = 3;
    private List<MyBean> mAllData;
    private List<MyBean> mMyPresets = new ArrayList<>();
    private List<MyBean> mMyCustoms = new ArrayList<>();

    private final LayoutInflater mInflater;
    private RecyclerView mRecyclerView;

    public MyAdapter(Context context, List<MyBean> datas, RecyclerView recyclerView) {
        this.mAllData = datas;
        mInflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;
        for (int i = 0; i < mAllData.size(); i++) {
            if (mAllData.get(i).getTypeView() == 2) {
                mMyPresets.add(mAllData.get(i));
            } else if (mAllData.get(i).getTypeView() == 3) {
                mMyCustoms.add(mAllData.get(i));
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = null;
        if (viewType == MY_TITLE) {
            view = mInflater.inflate(R.layout.my_title, parent, false);
        } else {
            view = mInflater.inflate(R.layout.my_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextView channel = holder.getView(R.id.src);
        if (channel != null) {
            channel.setText(mAllData.get(position).getSrc());
        }
        TextView title = holder.getView(R.id.title);
        if (title != null) {
            title.setText(mAllData.get(position).getSrc());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        if (mAllData.get(holder.getAdapterPosition()).getTypeView() == MY_PRESET
                || mAllData.get(holder.getAdapterPosition()).getTypeView() == MY_CUSTOM) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onStartDragListener != null) {
                        onStartDragListener.startDrag(holder);
                    }
                    return true;
                }
            });
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {

                        case MotionEvent.ACTION_MOVE:
                            if (onStartDragListener != null) {
                                onStartDragListener.startDrag(holder);
                            }
                            break;
                    }
                    return false;
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return mAllData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mAllData.get(position).getTypeView() == 1) {
            return MY_TITLE;
        } else if (mAllData.get(position).getTypeView() == 2) {
            return MY_PRESET;
        } else {
            return MY_CUSTOM;
        }
    }

    private void move(int position) {

        if (position > mMyPresets.size() + 1) {
            int i = position - 2 - mMyPresets.size();
            //其他
            MyBean item = mMyCustoms.get(position - 2 - mMyPresets.size());
            mMyCustoms.remove(item);
            item.setTypeView(2);
            mMyPresets.add(item);
            notifyData();
            notifyItemMoved(position, mMyPresets.size());

        } else if (position > 0 && position <= mMyPresets.size()) {
            //我的
            MyBean item = mMyPresets.get(position - 1);
            mMyPresets.remove(item);
            item.setTypeView(3);
            mMyCustoms.add(0, item);

            notifyData();
            notifyItemMoved(position, mMyPresets.size() + 2);
        }
    }


    @Override
    public void itemMoved(int oldPosition, int newPosition) {

        if (mAllData.get(oldPosition).getTypeView() == MY_PRESET) {
            MyBean myBean = mMyPresets.get(oldPosition - 1);
            mMyPresets.remove(oldPosition - 1);
            mMyPresets.add(newPosition - 1, myBean);
            notifyData();
            notifyItemMoved(oldPosition, newPosition);
        } else {
            MyBean myBean = mMyCustoms.get(oldPosition - 6);
            mMyCustoms.remove(oldPosition - 6);
            mMyCustoms.add(newPosition - 6, myBean);
            notifyData();
            notifyItemMoved(oldPosition, newPosition);
        }
    }

    private void notifyData() {
        mAllData.clear();
        mAllData.add(new MyBean("Preset", 1));
        mAllData.addAll(mMyPresets);
        mAllData.add(new MyBean("Custom", 1));
        mAllData.addAll(mMyCustoms);
    }

    @Override
    public void itemDismiss(int position) {
    }

    private OnStartDragListener onStartDragListener;

    public interface OnStartDragListener {
        void startDrag(RecyclerView.ViewHolder holder);
    }

    public void setOnStartDragListener(OnStartDragListener onStartDragListener) {

        this.onStartDragListener = onStartDragListener;
    }
}