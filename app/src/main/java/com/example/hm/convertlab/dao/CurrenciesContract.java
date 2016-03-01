package com.example.hm.convertlab.dao;

/**
 * Created by hm on 15.02.2016.
 */
public class CurrenciesContract {
    BankContract daoBankConstants = new BankContract();

    public static final String TABLE_BANK_CURRENCIES = "currencies";

    public static final String KEY_ID1 = "id";
    public static final String KEY_CUR = "cur_id";
    public static final String KEY_CURRENCIES_NAME = "currencies_name";
    public static final String KEY_CURRENCIES_FULL_NAME = "currencies_full_name";
    public static final String KEY_BID = "bid";
    public static final String KEY_ASK = "ask";
    public static final String CHANGE_CUR = "change_cur";

    public static String CREATE_TABLE_CURRENCIES = " CREATE TABLE " + TABLE_BANK_CURRENCIES +" ("
            + KEY_ID1 + " INTEGER PRIMARY KEY, "
            + KEY_CURRENCIES_NAME + " TEXT, "
            + KEY_CURRENCIES_FULL_NAME + " TEXT, "
            + KEY_ASK + " TEXT, "
            + KEY_BID + " TEXT, "
            + CHANGE_CUR + "INTEGER, "
            + KEY_CUR + " INTEGER, "
            + " FOREIGN  KEY (" + KEY_CUR + ")" + " REFERENCES " +
            BankContract.TABLE_BANKS + "(" + BankContract.KEY_ID + "))";
}
