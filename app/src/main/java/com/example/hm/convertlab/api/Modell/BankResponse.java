package com.example.hm.convertlab.api.Modell;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hm on 26.01.2016.
 */
public class BankResponse {

    @SerializedName("organizations") public List<Banks> mBanks = new ArrayList<>();
}
