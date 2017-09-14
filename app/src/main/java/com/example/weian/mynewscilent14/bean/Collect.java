package com.example.weian.mynewscilent14.bean;

/**
 * Created by weian on 2017/7/5.
 */

public class Collect {
    private String imgurl;
    private String title;
    private String source;
    private String url;

    public Collect(String imgurl, String title, String source, String url) {
        this.imgurl = imgurl;
        this.title = title;
        this.source = source;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "imgurl='" + imgurl + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
