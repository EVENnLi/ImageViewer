package com.example.imageviewer.model;

import com.example.imageviewer.ImageContact;
import com.example.imageviewer.base.HttpResponseListener;

public class ImgModel implements ImageContact.ImageMdl {

    @Override
    public void getMore(String uri, HttpResponseListener callback) {
        //发送网络请求

        //解析

        //分情况使用callback的方法
        //成功了把数据传回去

        //失败了就把错误信息传回去
    }
}
