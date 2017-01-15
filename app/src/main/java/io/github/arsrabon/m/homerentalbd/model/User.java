package io.github.arsrabon.m.homerentalbd.model;

/**
 * Created by msrabon on 1/15/17.
 */

public class User {
    private int id;
    private String user_id;
    private String username;
    private String fullname;
    private String email;
    private String mobile_no;
    private String address;

    public User() {
    }

    public User(int id, String user_id, String username, String fullname, String email, String mobile_no, String address) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.mobile_no = mobile_no;
        this.address = address;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
