package io.github.arsrabon.m.homerentalbd.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by msrabon on 1/14/17.
 */

public class ApiClient {
    public static final String BASE_URL = "https://homerentalbd.000webhostapp.com/hrbd_api/v1/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
