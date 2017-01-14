package io.github.arsrabon.m.homerentalbd.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msrabon on 1/14/17.
 */

public class RentTypeResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("types")
    private List<RentTypes> rentTypesList = new ArrayList<>();

    public RentTypeResponse() {
    }

    public RentTypeResponse(boolean error, List<RentTypes> rentTypesList) {
        this.error = error;
        this.rentTypesList = rentTypesList;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<RentTypes> getRentTypesList() {
        return rentTypesList;
    }

    public void setRentTypesList(List<RentTypes> rentTypesList) {
        this.rentTypesList = rentTypesList;
    }
}
