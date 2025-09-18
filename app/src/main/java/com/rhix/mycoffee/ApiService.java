package com.rhix.mycoffee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("retrieve.php")
    Call<CoffeeResponse> getCoffees();

    // New endpoint for inquiry
    @POST("inquire.php")
    Call<InquiryResponse> submitInquiry(@Body InquiryRequest inquiryRequest);
}
