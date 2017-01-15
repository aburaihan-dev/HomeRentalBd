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
    private User user;

    public UserResponse() {
    }

    public UserResponse(boolean error, User user) {
        this.error = error;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
