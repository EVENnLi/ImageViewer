package com.example.imageviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageviewer.R;
import com.example.imageviewer.bean.ImageItem;

import java.util.List;

/**
 * @author EvenLi
 */

public class MultiTypeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ImageItem> dataList;
    private Context mContext;

    /**
     * 取得数据
     * @param dataList 装载有数据类的List
     * @param context  上下文
     */
    public MultiTypeRecyclerAdapter(List<ImageItem> dataList,Context context) {
        this.dataList = dataList;
        mContext=context;
    }

    /**
     *创建ViewHolder
     * @param parent 父View
     * @param viewType 子项的种类
     * @return 新的viewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View inflate;
        inflate= LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
       viewHolder=new ImageItemHolder(inflate);
        return viewHolder;
    }

    /**
     * 绑定控件
     * @author EvenLi
     * @param holder 子项的Holder实例
     * @param position 所在项的坐标
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            ImageItem item=(ImageItem) dataList.get(position);
            ImageItemHolder itemHolder=(ImageItemHolder) holder;
            //绑定图片

    }

    @Override
    public int getItemCount() {
        return dataList.size()+1;
    }



    /**
     * 图片布局的Holder
     */
    class ImageItemHolder extends RecyclerView.ViewHolder{
        ImageView view1;
        TextView text1;
        public ImageItemHolder(@NonNull View itemView) {
            super(itemView);
            view1=itemView.findViewById(R.id.image_view_one);
            text1=itemView.findViewById(R.id.author_text_one);
        }
    }


}
