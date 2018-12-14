package com.example.kokihoon.myappointment.data;

public class SingerItem {

    public String title;
    public String link;
    public String image;
    public String subtitle;
    public String pubDate;
    public String director;
    public String actor;
    public String userRating;

    public SingerItem(String link, String image, String subtitle, String title, String pubDate, String director, String actor, String userRating) {
        this.link = link;
        this.image = image;
        this.subtitle = subtitle;
        this.title = title;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
        this.userRating = userRating;
    }



}
