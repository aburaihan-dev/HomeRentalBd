package io.github.arsrabon.m.homerentalbd.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msrabon on 1/14/17.
 */

public class AreaResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("areas")
    private List<Area> areaList = new ArrayList<>();

    public AreaResponse() {
    }

    public AreaResponse(boolean error, List<Area> areaList) {
        this.error = error;
        this.areaList = areaList;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
