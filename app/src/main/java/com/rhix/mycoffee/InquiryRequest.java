package com.rhix.mycoffee;

public class InquiryRequest {
    private String coffee_name;
    private String name;
    private String email;
    private String inquiry;

    public InquiryRequest(String coffee_name, String name, String email, String inquiry) {
        this.coffee_name = coffee_name;
        this.name = name;
        this.email = email;
        this.inquiry = inquiry;
    }


}
