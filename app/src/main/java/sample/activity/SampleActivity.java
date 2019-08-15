package sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import sample.activity.R;
import sample.activity.channel.MyAdapter;
import sample.activity.channel.MyBean;
import sample.activity.channel.TouchListener;


public class SampleActivity extends AppCompatActivity implements MyAdapter.OnStartDragListener {

    private RecyclerView channelRecyler;
    private MyAdapter mAdapter;
    private ItemTouchHelper mTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        channelRecyler = (RecyclerView) findViewById(R.id.channl_recyler);
        mAdapter = new MyAdapter(this,getDatas(),channelRecyler);
        GridLayoutManager manager=new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                if(itemViewType==mAdapter.MY_TITLE){
                    return 2;
                }
                return 1;
            }
        });
        channelRecyler.setLayoutManager(manager);
        channelRecyler.setAdapter(mAdapter);
        //创建ItemTouchHelper
        mTouchHelper = new ItemTouchHelper(new TouchListener(mAdapter));
        //attach到RecyclerView中
        mTouchHelper.attachToRecyclerView(channelRecyler);
        mAdapter.setOnStartDragListener(this);
    }

    private String[] EqPreset = {"OFF","JAZZ","VOCAL","BASS"};
    private List<MyBean> getDatas(){
        List<MyBean> beanList=new ArrayList<>();
        beanList.add(new MyBean("Preset",1,MyAdapter.MY_TITLE_TYPE_GONE));

        for(int i=0;i<4;i++){
            beanList.add(new MyBean(EqPreset[i],2,MyAdapter.MY_TITLE_TYPE_NONE));
        }

        beanList.add(new MyBean("Custom",1,MyAdapter.MY_TITLE_TYPE_GONE));

        for(int i=0;i<10;i++){
            beanList.add(new MyBean("My EQ"+i,3,MyAdapter.MY_TITLE_TYPE_NONE));
        }

        return beanList;

    }

    @Override
    public void startDrag(RecyclerView.ViewHolder holder) {
        mTouchHelper.startDrag(holder);
    }
}
