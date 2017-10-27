package com.adim.techease.controllers;

/**
 * Created by Adam Noor on 25-Oct-17.
 */

public class TvModel {

    private String linkTv;
    private String TypeTv;
    private String ThumbnailTv;
    private String TitleTv;

    public String getTitleTv() {
        return TitleTv;
    }

    public void setTitleTv(String titleTv) {
        TitleTv = titleTv;
    }



    public String getLinkTv() {
        return linkTv;
    }

    public void setLinkTv(String linkTv) {
        this.linkTv = linkTv;
    }

    public String getTypeTv() {
        return TypeTv;
    }

    public void setTypeTv(String typeTv) {
        TypeTv = typeTv;
    }

    public String getThumbnailTv() {
        return ThumbnailTv;
    }

    public void setThumbnailTv(String thumbnailTv) {
        ThumbnailTv = thumbnailTv;
    }



}
