package com.example.hm.convertlab.dao;

/**
 * Created by hm on 15.02.2016.
 */
public class BankContract {

    public static String TABLE_BANKS = "banks";

    public static final String KEY_ID = "id";
    public static final String KEY_BANK_ID = "bank_id";
    public static final String KEY_BANK_NAME = "bank_name";
    public static final String KEY_BANK_REGION = "bank_region";
    public static final String KEY_BANK_CITY = "bank_city";
    public static final String KEY_BANK_PH_NO = "bank_phone_number";
    public static final String KEY_BANK_ADDRESS = "bank_address";
    public static final String KEY_BANK_LINK = "bank_link";

    public static String CREATE_BANKS_TABLE = "CREATE TABLE " + TABLE_BANKS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BANK_ID + " TEXT,"
            + KEY_BANK_NAME + " TEXT,"
            + KEY_BANK_REGION + " TEXT,"
            + KEY_BANK_CITY + " TEXT,"
            + KEY_BANK_PH_NO + " TEXT,"
            + KEY_BANK_ADDRESS + " TEXT,"
            + KEY_BANK_LINK + " TEXT"
            + ")";
}
