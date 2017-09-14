package com.example.weian.mynewscilent14.bean;

/**
 * Created by weian on 2017/6/20.
 */

public class News {
    private String imgsrc;
    private String title;
    private String url_3w,replyCount;
    private int hasHead,hasImg,imgType;
    //最后修改时间，发布时间
    private String lmodify,ptime;
    //来源
    private String source;

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public int getImgType() {
        return imgType;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public void setImgType(int imgType) {
        this.imgType = imgType;
    }

    public News(String imgsrc, String title, String url_3w, String replyCount, int hasHead, int hasImg, int imgType, String lmodify, String ptime, String source) {
        this.imgsrc = imgsrc;
        this.title = title;
        this.url_3w = url_3w;
        this.replyCount = replyCount;
        this.hasHead = hasHead;
        this.hasImg = hasImg;
        this.imgType = imgType;
        this.lmodify = lmodify;
        this.ptime = ptime;
        this.source = source;
    }

    @Override
    public String toString() {
        return "News{" +
                "imgsrc='" + imgsrc + '\'' +
                ", title='" + title + '\'' +
                ", url_3w='" + url_3w + '\'' +
                ", lmodify='" + lmodify + '\'' +
                ", ptime='" + ptime + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_3w() {
        return url_3w;
    }

    public void setUrl_3w(String url_3w) {
        this.url_3w = url_3w;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
