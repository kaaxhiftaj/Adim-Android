package com.adim.techease.controllers;

/**
 * Created by Adam Noor on 26-Oct-17.
 */

public class TeamModel {
    public String getTeamTitle() {
        return TeamTitle;
    }

    public void setTeamTitle(String teamTitle) {
        TeamTitle = teamTitle;
    }

    public String getTeamDesignation() {
        return TeamDesignation;
    }

    public void setTeamDesignation(String teamDesignation) {
        TeamDesignation = teamDesignation;
    }

    public String getTeamDescription() {
        return TeamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        TeamDescription = teamDescription;
    }

    public String getTeamImage() {
        return TeamImage;
    }

    public void setTeamImage(String teamImage) {
        TeamImage = teamImage;
    }

    private String TeamTitle;
    private String TeamDesignation;
    private String TeamDescription;
    private String TeamImage;
}
