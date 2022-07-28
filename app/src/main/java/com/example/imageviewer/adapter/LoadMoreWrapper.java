package com.example.imageviewer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageviewer.R;

/**
 * @author evenli
 */
public class LoadMoreWrapper extends RecyclerView.Adapter {
    //要装饰的adapter
    RecyclerView.Adapter adapter;

    //加载状态，默认加载完成
    private int loadState=2;
    //加载中
    public static final int LOADING=1;
    //加载完成
    public static final int LOAD_COMPLETE=2;
    //加载失败
    public static final int LOAD_FAILED=3;

    //布局种类
    private final int IMAGE_ITEM=4;
    private final int PROGRESS_ITEM=5;

    public LoadMoreWrapper(RecyclerView.Adapter adapter1) {
        adapter=adapter1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==adapter.getItemCount()-1){
            return PROGRESS_ITEM;
        }else{
            return IMAGE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case IMAGE_ITEM:{
                return adapter.onCreateViewHolder(parent,viewType);
            }
            case PROGRESS_ITEM:{
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_item,parent,false);
                return new ProgressbarHolder(view);
            }
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProgressbarHolder){
            ProgressbarHolder pHolder=(ProgressbarHolder) holder;
            ProgressBar progressBar=pHolder.progressBar;
            TextView text=pHolder.loadingText;
            switch (loadState){
                //TODO:具体的加载事件的文本显示
                case LOADING:

                case LOAD_COMPLETE:

                case LOAD_FAILED:

                default:

            }

        }else{
            adapter.onBindViewHolder(holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    /**
     * 加载项的Holder
     */
    class ProgressbarHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        TextView  loadingText;

        public ProgressbarHolder(@NonNull View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progress_bar);
            loadingText=itemView.findViewById(R.id.loading_text_view);
        }
    }

    /**
     * 重写该方法，使得加载头占两格
     * @param recyclerView 实例
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager=recyclerView.getLayoutManager();
        //判断是否网格布局
        if(manager instanceof GridLayoutManager){
            final GridLayoutManager gridManager=(GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //如果当前是加载头的位置，该item占据两格，否则正常情况为一格
                    return getItemViewType(position)==PROGRESS_ITEM?gridManager.getSpanCount():1;
                }
            });
        }
    }

    /**
     * 用于设置上拉头的加载状态
     * @param loadState
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
