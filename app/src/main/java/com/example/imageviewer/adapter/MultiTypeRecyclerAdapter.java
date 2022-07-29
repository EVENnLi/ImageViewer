package com.example.imageviewer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageviewer.MainActivity;
import com.example.imageviewer.R;
import com.example.imageviewer.bean.ImageItem;
import com.example.imageviewer.test.TestActivity;
import com.example.imageviewer.util.ImageLoader;

import java.io.Serializable;
import java.util.List;

/**
 * @author EvenLi
 */

public class MultiTypeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImageItem> dataList;
    private Context mContext;
    private ImageLoader mLoader;

    /**
     * 取得数据
     *
     * @param dataList 装载有数据类的List
     * @param context  上下文
     */
    public MultiTypeRecyclerAdapter(List<ImageItem> dataList, Context context) {
        this.dataList = dataList;
        mContext = context;
        mLoader = ImageLoader.build(context);
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   父View
     * @param viewType 子项的种类
     * @return 新的viewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View inflate;
        inflate = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        viewHolder = new ImageItemHolder(inflate);
        return viewHolder;
    }

    /**
     * 绑定控件
     *
     * @param holder   子项的Holder实例
     * @param position 所在项的坐标
     * @author EvenLi
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.d("TAG", "onBindViewHolder: ");
        ImageItem item = dataList.get(position);
        ImageItemHolder itemHolder = (ImageItemHolder) holder;
        ImageView view = itemHolder.view1;
        //绑定作者信息
        itemHolder.text1.setText(item.getAuthor());
        //绑定图片
        //宽高不确定到时候再说
        mLoader.bindBitmap(item.getDownLoadUri(), view, 200, 90);



     /*   Runnable task=(()->{
            Bitmap bitmap= AboutUrl.downloadBitmapFromUrl(item.getDownLoadUri());

            Handler mHandler=new Handler(Looper.getMainLooper());

            mHandler.post(()->{
                view.setImageBitmap(bitmap);
            });

        });

        ThreadPoolUtil.S_THREAD_POOL_EXECUTOR.execute(task);
*/


    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }


    /**
     * 图片布局的Holder
     */
    class ImageItemHolder extends RecyclerView.ViewHolder {
        ImageView view1;
        TextView text1;

        public ImageItemHolder(@NonNull View itemView) {
            super(itemView);
            view1 = itemView.findViewById(R.id.image_view_one);
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, TestActivity.class);
                    intent.putExtra("datalist", (Serializable) dataList);
                    intent.putExtra("position",0);
                    ((Activity)mContext).startActivityForResult(intent,0);
                }
            });
            text1 = itemView.findViewById(R.id.author_text_one);
        }
    }


}
