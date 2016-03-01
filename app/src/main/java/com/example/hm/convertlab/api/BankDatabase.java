package com.example.hm.convertlab.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hm.convertlab.dao.BankContract;
import com.example.hm.convertlab.dao.CurrenciesContract;

/**
 * Created by hm on 10.02.2016.
 */
public class BankDatabase extends SQLiteOpenHelper {

    private static BankDatabase sInstance;

    public static void init(Context _context) {
        if (sInstance == null) {
            sInstance = new BankDatabase(_context.getApplicationContext());
        }
    }

    public static BankDatabase getInstance() {
        return sInstance;
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "banksManager";


    private BankDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(new BankContract().CREATE_BANKS_TABLE);
        db.execSQL(new CurrenciesContract().CREATE_TABLE_CURRENCIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
