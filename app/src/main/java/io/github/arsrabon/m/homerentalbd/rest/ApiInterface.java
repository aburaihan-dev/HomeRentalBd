package io.github.arsrabon.m.homerentalbd.rest;

import io.github.arsrabon.m.homerentalbd.model.AreaResponse;
import io.github.arsrabon.m.homerentalbd.model.PostResponse;
import io.github.arsrabon.m.homerentalbd.model.Rent;
import io.github.arsrabon.m.homerentalbd.model.RentResponse;
import io.github.arsrabon.m.homerentalbd.model.RentTypeResponse;
import io.github.arsrabon.m.homerentalbd.model.RentalAdResponse;
import io.github.arsrabon.m.homerentalbd.model.Reviews;
import io.github.arsrabon.m.homerentalbd.model.ReviewsResponse;
import io.github.arsrabon.m.homerentalbd.model.UserResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @FormUrlEncoded
    @POST("reviews")
    Call<PostResponse> postReviews(@Field("rents_id") int rents_id,
                                   @Field("user_id") String user_id,
                                   @Field("rating") int rating,
                                   @Field("review") String review);

    @FormUrlEncoded
    @POST("user")
    Call<PostResponse> createNewUser(@Field("user_id") String user_id,
                                     @Field("Username") String Username,
                                     @Field("email") String email);

    @GET("user/user_id/{id}")
    Call<UserResponse> getUserByUserId(@Path("id") String id);

    @FormUrlEncoded
    @PUT("user")
    Call<PostResponse> updateNewUser(@Field("user_id") String user_id,
                                     @Field("Username") String Username,
                                     @Field("fullName") String fullname,
                                     @Field("email") String email,
                                     @Field("mobile_no") String mobile_no,
                                     @Field("address") String address);

}
