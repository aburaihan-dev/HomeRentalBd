package io.github.arsrabon.m.homerentalbd.model;

/**
 * Created by msrabon on 1/14/17.
 */

public class Area {
    private int id;
    private int city_id;
    private String area;

    public Area() {
    }

    public Area(int id, int city_id, String area) {
        this.id = id;
        this.city_id = city_id;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return area;
    }
}
