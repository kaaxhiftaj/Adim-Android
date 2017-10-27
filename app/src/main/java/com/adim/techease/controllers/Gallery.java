package com.adim.techease.controllers;

/**
 * Created by Adam Noor on 20-Oct-17.
 */

public class Gallery {
    private String Title;
    private String link;
    private String Type;
    private String Thumbnail;

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
