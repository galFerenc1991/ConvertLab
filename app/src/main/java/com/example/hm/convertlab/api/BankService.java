package com.example.hm.convertlab.api;

import com.example.hm.convertlab.api.Modell.BankResponse;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by hm on 26.01.2016.
 */
public interface BankService {
    @GET("/ru/public/currency-cash.json")
    BankResponse getBanks();
}
