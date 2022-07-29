package com.example.imageviewer.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.imageviewer.R;
import com.example.imageviewer.bean.ImageItem;
import com.example.imageviewer.util.ImageLoader;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private List<ImageItem> datalist;
    private int position;
    private HorizonSrollViewEx horizonSrollViewEx;
    private ImageLoader loader;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        LinearLayout layout=findViewById(R.id.layout);
        horizonSrollViewEx=new HorizonSrollViewEx(this);
        Intent intent=getIntent();


        loader=ImageLoader.build(this);

        datalist=(List<ImageItem>) intent.getSerializableExtra("datalist");
        Log.e("TAG", "onCreate: "+datalist.toString() );


        position=intent.getIntExtra("position",0);
        Log.e("TAG", "onCreate: position:"+position );



        for (int i = 0; i < datalist.size(); i++) {
            final String uri=datalist.get(i).getDownLoadUri();
            final ImageView view=new ImageView(this);
            ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);



            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            view.setLayoutParams(layoutParams);

            loader.bindBitmap(uri,view,0,0);
            horizonSrollViewEx.addView(view);


        }


            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            horizonSrollViewEx.setLayoutParams(params);

        screenWidth=horizonSrollViewEx.getScreenWidth();
        Log.e("TAG", "onCreate: 屏幕宽度"+screenWidth );


        layout.addView(horizonSrollViewEx);



    }



}