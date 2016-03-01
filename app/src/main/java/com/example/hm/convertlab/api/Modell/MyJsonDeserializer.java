package com.example.hm.convertlab.api.Modell;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hm on 29.01.2016.
 */
public class MyJsonDeserializer implements JsonDeserializer<BankResponse> {
    final String TAG = "myLogs";

    public BankResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BankResponse bankResponse = new BankResponse();
        bankResponse.mBanks = new ArrayList<>();

        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonArray jsonArrayBanks = jsonObject.get("organizations").getAsJsonArray();
        Map<String, String> map = new HashMap<>();
        JsonObject currenciesFullName = jsonObject.get("currencies").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> curList = currenciesFullName.entrySet();
        for (Map.Entry<String,JsonElement>myMap : curList) {
            map.put(myMap.getKey(), myMap.getValue().getAsString());
        }


        for (int i = 0; i < jsonArrayBanks.size();i++) {
                Banks bank = new Banks();
                bank.mBankName = jsonArrayBanks.get(i).getAsJsonObject().get("title").getAsString();
                bank.mBankID = jsonArrayBanks.get(i).getAsJsonObject().get("id").getAsString();
                if (!jsonArrayBanks.get(i).getAsJsonObject().get("phone").isJsonNull())
                    bank.mPhoneNumber = jsonArrayBanks.get(i).getAsJsonObject().get("phone").getAsString();
                else
                    bank.mPhoneNumber = "No number";
                Log.d(TAG,"element =" +i);

                bank.mAddress = jsonArrayBanks.get(i).getAsJsonObject().get("address").getAsString();

                String mRegionId = jsonArrayBanks.get(i).getAsJsonObject().get("regionId").getAsString();
                String mCityId = jsonArrayBanks.get(i).getAsJsonObject().get("cityId").getAsString();

                bank.mRegion = jsonObject.get("regions").getAsJsonObject().get(mRegionId).getAsString();
                bank.mCity = jsonObject.get("cities").getAsJsonObject().get(mCityId).getAsString();
                bank.mLink = jsonArrayBanks.get(i).getAsJsonObject().get("link").getAsString();

                List <Currencies> _currencies = new ArrayList<>();
                Currencies cup;
                JsonObject currencies = jsonArrayBanks.get(i).getAsJsonObject().get("currencies").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> currenciesList = currencies.entrySet();
                for (Map.Entry<String, JsonElement> valuta: currenciesList){
                    cup = new Currencies();
                    cup.CurrenciesName = valuta.getKey();
                    cup.mCurrenciesFullName = map.get(cup.CurrenciesName);
                    cup.mBid = valuta.getValue().getAsJsonObject().get("bid").getAsString();
                    cup.mAsk = valuta.getValue().getAsJsonObject().get("ask").getAsString();

                    _currencies.add(cup);
                    bank.mCurrencies = _currencies;
                }

                bankResponse.mBanks.add(i, bank);
            }

        return bankResponse;
    }
}
