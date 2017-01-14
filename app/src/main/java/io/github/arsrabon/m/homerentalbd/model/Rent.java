package io.github.arsrabon.m.homerentalbd.model;

import java.util.Date;

/**
 * Created by msrabon on 1/14/17.
 */

public class Rent {
    private int id;
    private String user_id;
    private int area_id;
    private int rent_type_id;
    private String banner;
    private int beds;
    private int baths;
    private int size;
    private String floordetails;
    private boolean lift;
    private boolean parking;
    private int rentprice;
    private String rentdetails;
    private String address;
    private double geoloc_lat;
    private double geoloc_lng;
    private String available;
    private String img_banner;
    private String img_other_one;
    private String img_other_two;
    private String created_at;

    public Rent() {
    }

    public Rent(int id, String user_id, int area_id, int rent_type_id, String banner, int beds,
                int baths, int size, String floordetails, boolean lift, boolean parking, int rentprice,
                String rentdetails, String address, double geoloc_lat, double geoloc_lng, String available,
                String img_banner, String img_other_one, String img_other_two, String created_at) {
        this.id = id;
        this.user_id = user_id;
        this.area_id = area_id;
        this.rent_type_id = rent_type_id;
        this.banner = banner;
        this.beds = beds;
        this.baths = baths;
        this.size = size;
        this.floordetails = floordetails;
        this.lift = lift;
        this.parking = parking;
        this.rentprice = rentprice;
        this.rentdetails = rentdetails;
        this.address = address;
        this.geoloc_lat = geoloc_lat;
        this.geoloc_lng = geoloc_lng;
        this.available = available;
        this.img_banner = img_banner;
        this.img_other_one = img_other_one;
        this.img_other_two = img_other_two;
        this.created_at = created_at;
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

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getRent_type_id() {
        return rent_type_id;
    }

    public void setRent_type_id(int rent_type_id) {
        this.rent_type_id = rent_type_id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBaths() {
        return baths;
    }

    public void setBaths(int baths) {
        this.baths = baths;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFloordetails() {
        return floordetails;
    }

    public void setFloordetails(String floordetails) {
        this.floordetails = floordetails;
    }

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public int getRentprice() {
        return rentprice;
    }

    public void setRentprice(int rentprice) {
        this.rentprice = rentprice;
    }

    public String getRentdetails() {
        return rentdetails;
    }

    public void setRentdetails(String rentdetails) {
        this.rentdetails = rentdetails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getGeoloc_lat() {
        return geoloc_lat;
    }

    public void setGeoloc_lat(double geoloc_lat) {
        this.geoloc_lat = geoloc_lat;
    }

    public double getGeoloc_lng() {
        return geoloc_lng;
    }

    public void setGeoloc_lng(double geoloc_lng) {
        this.geoloc_lng = geoloc_lng;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getImg_banner() {
        return img_banner;
    }

    public void setImg_banner(String img_banner) {
        this.img_banner = img_banner;
    }

    public String getImg_other_one() {
        return img_other_one;
    }

    public void setImg_other_one(String img_other_one) {
        this.img_other_one = img_other_one;
    }

    public String getImg_other_two() {
        return img_other_two;
    }

    public void setImg_other_two(String img_other_two) {
        this.img_other_two = img_other_two;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
