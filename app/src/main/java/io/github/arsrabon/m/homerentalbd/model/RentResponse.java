package io.github.arsrabon.m.homerentalbd.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msrabon on 1/14/17.
 */
public class RentResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("rents")
    private List<Rent> rents = new ArrayList<>();

    public RentResponse() {
    }

    public RentResponse(boolean error, List<Rent> rents) {
        this.error = error;
        this.rents = rents;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }
}
