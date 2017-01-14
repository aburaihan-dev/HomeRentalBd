package io.github.arsrabon.m.homerentalbd.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msrabon on 1/14/17.
 */

public class RentalAdResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("rents")
    private List<RentalAd> rentalAds = new ArrayList<>();

    public RentalAdResponse() {
    }

    public RentalAdResponse(boolean error, List<RentalAd> rentalAds) {
        this.error = error;
        this.rentalAds = rentalAds;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<RentalAd> getRentalAds() {
        return rentalAds;
    }

    public void setRentalAds(List<RentalAd> rentalAds) {
        this.rentalAds = rentalAds;
    }
}
