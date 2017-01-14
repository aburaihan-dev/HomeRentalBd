package io.github.arsrabon.m.homerentalbd.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msrabon on 1/15/17.
 */

public class ReviewsResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("review")
    private List<Reviews> reviewsList = new ArrayList<>();

    public ReviewsResponse() {
    }

    public ReviewsResponse(boolean error, List<Reviews> reviewsList) {
        this.error = error;
        this.reviewsList = reviewsList;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Reviews> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }
}
