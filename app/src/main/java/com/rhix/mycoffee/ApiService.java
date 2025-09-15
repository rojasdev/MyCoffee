package com.rhix.mycoffee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("retrieve.php")
    Call<CoffeeResponse> getCoffees();
}
