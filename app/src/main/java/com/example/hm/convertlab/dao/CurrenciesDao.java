package com.example.hm.convertlab.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hm.convertlab.api.BankDatabase;
import com.example.hm.convertlab.api.Modell.Banks;
import com.example.hm.convertlab.api.Modell.Currencies;

import static com.example.hm.convertlab.dao.CurrenciesContract.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hm on 15.02.2016.
 */
public class CurrenciesDao  {

    public List<Currencies> getCurrencies(int num){
        SQLiteDatabase db = null;
        List<Currencies> exampleCurrencies = new ArrayList<>();
        Cursor c = null;
        try {
            db = BankDatabase.getInstance().getReadableDatabase();
            c = db.query(TABLE_BANK_CURRENCIES, null, CurrenciesContract.KEY_CUR + " = ?", new String[]{String.valueOf(num)}, null, null, null);
            if (c.moveToFirst()) {
                int nameCurrenciesColIndex = c.getColumnIndex(KEY_CURRENCIES_NAME);
                int fullNameCurrenciesColIndex = c.getColumnIndex(KEY_CURRENCIES_FULL_NAME);
                int bidColIndex = c.getColumnIndex(KEY_BID);
                int askColIndex = c.getColumnIndex(KEY_ASK);

                do {
                    Currencies curExample = new Currencies();
                    curExample.CurrenciesName = c.getString(nameCurrenciesColIndex);
                    curExample.mCurrenciesFullName = c.getString(fullNameCurrenciesColIndex);
                    curExample.mAsk = c.getString(askColIndex);
                    curExample.mBid = c.getString(bidColIndex);

                    exampleCurrencies.add(curExample);
                } while (c.moveToNext());
            }else c.close();
        } catch (Exception e) {
            Log.d("Feri", "Exception", e);
        }

        return exampleCurrencies;
    }

    public void addCurrencies(long _bankKey, Banks _bank) {
        SQLiteDatabase database = BankDatabase.getInstance().getWritableDatabase();
        for (int j = 0; j < _bank.mCurrencies.size(); j++){
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_CURRENCIES_NAME, _bank.mCurrencies.get(j).CurrenciesName);
            contentValues.put(KEY_CURRENCIES_FULL_NAME, _bank.mCurrencies.get(j).mCurrenciesFullName);
            contentValues.put(KEY_ASK, _bank.mCurrencies.get(j).mAsk);
            contentValues.put(KEY_BID, _bank.mCurrencies.get(j).mBid);
            contentValues.put(KEY_CUR, _bankKey);


            database.insert(TABLE_BANK_CURRENCIES, null, contentValues);
        }
    }

    public void deleteCurrencies() {
        SQLiteDatabase db = BankDatabase.getInstance().getWritableDatabase();
        db.delete(TABLE_BANK_CURRENCIES, null, null);
    }
}
