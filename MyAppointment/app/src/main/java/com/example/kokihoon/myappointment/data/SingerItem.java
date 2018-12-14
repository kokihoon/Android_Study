package com.example.kokihoon.myappointment.data;

public class SingerItem {

    private String link;
    private String image;
    private String subtitle;
    private String title;
    private String puDate;
    private String director;
    private String actor;
    private String userRating;

    public SingerItem(String link, String image, String subtitle, String title, String puDate, String director, String actor, String userRating) {
        this.link = link;
        this.image = image;
        this.subtitle = subtitle;
        this.title = title;
        this.puDate = puDate;
        this.director = director;
        this.actor = actor;
        this.userRating = userRating;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPuDate() {
        return puDate;
    }

    public void setPuDate(String puDate) {
        this.puDate = puDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
}
