package io.github.arsrabon.m.homerentalbd.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msrabon on 1/15/17.
 */

public class UserResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("user")
    private List<User> users = new ArrayList<>();

    public UserResponse() {
    }

    public UserResponse(boolean error, List<User> user) {
        this.error = error;
        this.users = user;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
