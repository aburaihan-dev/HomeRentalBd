package io.github.arsrabon.m.homerentalbd.model;

/**
 * Created by msrabon on 1/14/17.
 */

public class RentTypes {
    private int id;
    private String type;

    public RentTypes() {
    }

    public RentTypes(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
