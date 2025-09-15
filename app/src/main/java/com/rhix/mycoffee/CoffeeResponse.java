package com.rhix.mycoffee;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CoffeeResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("items")
    private List<CoffeeBean> items;

    public String getMessage() {
        return message;
    }

    public List<CoffeeBean> getItems() {
        return items;
    }
}

