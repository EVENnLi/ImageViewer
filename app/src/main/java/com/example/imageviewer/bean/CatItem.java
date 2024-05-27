package com.example.imageviewer.bean;

import java.io.Serializable;

public class CatItem implements Serializable {
    String id;
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CatItem{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
