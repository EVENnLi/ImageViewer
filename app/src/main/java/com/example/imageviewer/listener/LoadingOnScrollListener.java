package com.example.imageviewer.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author evenli
 */
public abstract class LoadingOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 用来记录是否在向上滑动
     */
    private boolean isSlidingUpward=false;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager=(LinearLayoutManager) recyclerView.getLayoutManager();
        //不滑动时
        if(newState==RecyclerView.SCROLL_STATE_IDLE){
            //获取最后一个显示的子项位置
            if(manager!=null){
                int lastItemPosition=manager.findLastCompletelyVisibleItemPosition();
                int itemCount=manager.getItemCount();
                //判断是否滑动到了最后一个
                if(lastItemPosition==(itemCount-1)&&isSlidingUpward){
                    //加载更多
                    loadMore();
                }
            }
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //从dy的值判断是否向上滑动
        isSlidingUpward=dy>0;
    }

    /**
     * 给外界进行重写具体的加载逻辑
     */
    public abstract void loadMore();
}
