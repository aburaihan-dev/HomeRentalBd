package io.github.arsrabon.m.homerentalbd.model;

/**
 * Created by msrabon on 1/14/17.
 */

public class RentalAd {
    private int id;
    private String area;
    private String type;
    private String banner;
    private int beds;
    private int size;
    private int rentprice;
    private String available;
    private double avgrating;
    private String reviews;
    private String img_banner;

    public RentalAd() {
    }

    public RentalAd(int id, String area, String type, String banner, int beds, int size,
                    int rentprice, String available, double avgrating, String reviews, String img_banner) {
        this.id = id;
        this.area = area;
        this.type = type;
        this.banner = banner;
        this.beds = beds;
        this.size = size;
        this.rentprice = rentprice;
        this.available = available;
        this.avgrating = avgrating;
        this.reviews = reviews;
        this.img_banner = img_banner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRentprice() {
        return rentprice;
    }

    public void setRentprice(int rentprice) {
        this.rentprice = rentprice;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public double getAvgrating() {
        return avgrating;
    }

    public void setAvgrating(double avgrating) {
        this.avgrating = avgrating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getImg_banner() {
        return img_banner;
    }

    public void setImg_banner(String img_banner) {
        this.img_banner = img_banner;
    }
}