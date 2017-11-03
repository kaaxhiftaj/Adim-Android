package com.adim.techease.controllers;

/**
 * Created by kaxhiftaj on 10/22/17.
 */

public class VoteModel {

    private String Title;
    private String Image;
    private String Vote;

    public String getVoteContestentID() {
        return VoteContestentID;
    }

    public void setVoteContestentID(String voteContestentID) {
        VoteContestentID = voteContestentID;
    }

    private String VoteContestentID;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getVote() {
        return Vote;
    }

    public void setVote(String vote) {
        Vote = vote;
    }


}
