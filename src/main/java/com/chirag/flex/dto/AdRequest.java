package com.chirag.flex.dto;

import java.util.List;

public class AdRequest {
    private String name;
    private int duration;
    private int days;
    private List<String> places;

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public int getDuration(){return duration;}
    public void setDuration(int duration){this.duration=duration;}

    public int getDays(){return days;}
    public void setDays(int days){this.days=days;}

    public List<String> getPlaces(){return places;}
    public void setPlaces(List<String> places){this.places=places;}
}
