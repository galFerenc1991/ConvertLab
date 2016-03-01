package com.example.hm.convertlab.api;

import com.example.hm.convertlab.api.Modell.BankResponse;
import com.example.hm.convertlab.api.Modell.MyJsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by hm on 26.01.2016.
 */
public class RestProvider {

    private static RestProvider mInstance;

    private RestAdapter mRestAdapter;
    private BankService mBankService;

    private RestProvider() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BankResponse.class, new MyJsonDeserializer())
                .create();
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint("http://resources.finance.ua")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mBankService = mRestAdapter.create(BankService.class);
    }

    public static RestProvider getInstance(){
        if (mInstance == null){
            mInstance = new RestProvider();
        }

        return mInstance;
    }

    public BankService getBankService() {
        return mBankService;
    }
}
