package com.example.hm.convertlab.api.Modell;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hm on 01.03.2016.
 */
public class Banks implements Serializable {

    @SerializedName("id") public String mBankID;
    @SerializedName("title") public String mBankName;
    @SerializedName("regionId") public String mRegion;
    @SerializedName("cityId") public String mCity;
    @SerializedName("phone") public String mPhoneNumber;
    @SerializedName("adress") public String mAddress;
    @SerializedName("link") public String mLink;
    @SerializedName("currencies") public List<Currencies> mCurrencies;


}
