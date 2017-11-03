package com.adim.techease.controllers;

/**
 * Created by Adam Noor on 20-Oct-17.
 */

public class Gallery {


    private String Id;
    private String Title;
    private String link;
    private String Type;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
