package io.github.arsrabon.m.homerentalbd.model;

/**
 * Created by msrabon on 1/15/17.
 */

public class Reviews {
    private int id;
    private int rents_id;
    private String user_id;
    private String username;
    private int rating;
    private String review;

    public Reviews() {
    }

    public Reviews(int id, int rents_id, String user_id, String username, int rating, String review) {
        this.id = id;
        this.rents_id = rents_id;
        this.user_id = user_id;
        this.username = username;
        this.rating = rating;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRents_id() {
        return rents_id;
    }

    public void setRents_id(int rents_id) {
        this.rents_id = rents_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
