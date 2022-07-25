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
    public static final int IMAGE_ITEM=1;
    public static final int PROGRESS_BAR_ITEM=2;

    private List<Object> dataList;
    private Context mContext;

    /**
     * 取得数据
     * @param dataList 装载有数据类的List
     * @param context  上下文
     */
    public MultiTypeRecyclerAdapter(List<Object> dataList,Context context) {
        this.dataList = dataList;
        mContext=context;
    }

    /**
     *根据ViewType判断加哪一个布局
     * @param parent 父View
     * @param viewType 子项的种类
     * @return 返回不同的子项Holder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View inflate;

        switch (viewType){
            case IMAGE_ITEM:{
                inflate= LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
                viewHolder=new ImageItemHolder(inflate);
                break;
            }
            case PROGRESS_BAR_ITEM:{
                inflate=LayoutInflater.from(mContext).inflate(R.layout.progress_bar_item,parent,false);
                viewHolder=new ProgressbarHolder(inflate);
                break;
            }
            default:
        }
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
        if(getItemViewType(position)==IMAGE_ITEM){
            ImageItem item=(ImageItem) dataList.get(position);
            ImageItemHolder itemHolder=(ImageItemHolder) holder;
            ((ImageItemHolder) holder).view1.setImageBitmap(item.getBitmap1());
            ((ImageItemHolder) holder).text1.setText(item.getAuthor1());



             // @author Evenli
             // 下面搁这加点点击事件，待补充

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 其中ImageItem实例是图片，Object实例为progress
     * @param position 子项的坐标
     * @return 子项的种类
     */
    @Override
    public int getItemViewType(int position) {
        //获取当前项的实例
        Object o=dataList.get(position);
        //分辨该实例
        if(o instanceof ImageItem){
            return IMAGE_ITEM;
        }else{
            return PROGRESS_BAR_ITEM;
        }
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


    /**
     * 加载项的Holder
     */
    class ProgressbarHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;

        public ProgressbarHolder(@NonNull View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progress_bar);
        }
    }
}
