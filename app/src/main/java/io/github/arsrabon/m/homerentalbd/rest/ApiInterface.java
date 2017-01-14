package io.github.arsrabon.m.homerentalbd.rest;

import io.github.arsrabon.m.homerentalbd.model.AreaResponse;
import io.github.arsrabon.m.homerentalbd.model.Rent;
import io.github.arsrabon.m.homerentalbd.model.RentResponse;
import io.github.arsrabon.m.homerentalbd.model.RentTypeResponse;
import io.github.arsrabon.m.homerentalbd.model.RentalAdResponse;
import io.github.arsrabon.m.homerentalbd.model.ReviewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by msrabon on 1/14/17.
 */

public interface ApiInterface {

    @GET("rtype")
    Call<RentTypeResponse> getRentTypes();

    @GET("rtype/{id}")
    Call<RentTypeResponse> getSingleRentType(@Path("id") int id);

    @GET("areas")
    Call<AreaResponse> getAreas();

    @GET("areas/{id}")
    Call<AreaResponse> getSingleArea(@Path("id") int id);

    @GET("rents")
    Call<RentalAdResponse> getRentalAds();

    @GET("rents/{id}")
    Call<RentResponse> getSingleRentalAd(@Path("id") int id);

    @GET("reviews/{id}")
    Call<ReviewsResponse> getReviews(@Path("id") int rent_id);

}
