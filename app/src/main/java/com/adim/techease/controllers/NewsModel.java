package com.adim.techease.controllers;

/**
 * Created by Adam Noor on 26-Oct-17.
 */

public class NewsModel {

    public String getNewsid() {
        return Newsid;
    }

    public void setNewsid(String newsid) {
        Newsid = newsid;
    }

    public String getNewsDescription() {
        return NewsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        NewsDescription = newsDescription;
    }

    private String Newsid;
    private String NewsDescription;
    private String NewsImage;
    private String NewsTitle;


    public String getNewsImage() {
        return NewsImage;
    }

    public void setNewsImage(String newsImage) {
        NewsImage = newsImage;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }



}
