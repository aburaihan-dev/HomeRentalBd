package io.github.arsrabon.m.homerentalbd.model;

/**
 * Created by msrabon on 1/16/17.
 */

public class WishList {
    private int id;
    private String user_id;
    private int rent_id;

    public WishList() {
    }

    public WishList(int id, String user_id, int rent_id) {
        this.id = id;
        this.user_id = user_id;
        this.rent_id = rent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRent_id() {
        return rent_id;
    }

    public void setRent_id(int rent_id) {
        this.rent_id = rent_id;
    }
}
