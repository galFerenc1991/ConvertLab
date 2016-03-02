package com.example.hm.convertlab.database.dao;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hm.convertlab.MyNotification;
import com.example.hm.convertlab.database.BankDatabase;
import com.example.hm.convertlab.api.Modell.Banks;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hm on 15.02.2016.
 */
public class BankDao extends BankContract {

    CurrenciesDao currenciesDao = new CurrenciesDao();

    public void addAllBanks(List<Banks> mBanks){
        SQLiteDatabase db = BankDatabase.getInstance().getWritableDatabase();
        for (int i = 0; i < mBanks.size(); i++ ) {
            MyNotification.startNotification(mBanks.size(),i);
            Log.d("Loading", ":"+i);
            ContentValues cv = new ContentValues();
            cv.put(KEY_BANK_ID, mBanks.get(i).mBankID);
            cv.put(KEY_BANK_NAME, mBanks.get(i).mBankName);
            cv.put(KEY_BANK_REGION, mBanks.get(i).mRegion);
            cv.put(KEY_BANK_CITY, mBanks.get(i).mCity);
            cv.put(KEY_BANK_PH_NO, mBanks.get(i).mPhoneNumber);
            cv.put(KEY_BANK_ADDRESS, mBanks.get(i).mAddress);
            cv.put(KEY_BANK_LINK, mBanks.get(i).mLink);

            long primary = db.insert(BankContract.TABLE_BANKS, null, cv);

            currenciesDao.addCurrencies(primary, mBanks.get(i));
        }
        MyNotification.completeNotification();
        db.close();
    }

    public List<Banks> getBank(){
        Log.d("Upload", ":");

        SQLiteDatabase db = BankDatabase.getInstance().getReadableDatabase();
        List<Banks> example = new ArrayList<>();
        Cursor c = db.query(TABLE_BANKS, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int bankPosition = c.getColumnIndex(KEY_ID);
            int idColIndex = c.getColumnIndex(KEY_BANK_ID);
            int nameColIndex = c.getColumnIndex(KEY_BANK_NAME);
            int regionColIndex = c.getColumnIndex(KEY_BANK_REGION);
            int cityColIndex = c.getColumnIndex(KEY_BANK_CITY);
            int phoneColIndex = c.getColumnIndex(KEY_BANK_PH_NO);
            int addressColIndex = c.getColumnIndex(KEY_BANK_ADDRESS);
            int emailColIndex = c.getColumnIndex(KEY_BANK_LINK);
            do {
                Banks bankExample = new Banks();
                bankExample.mBankID = c.getString(idColIndex);
                bankExample.mBankName = c.getString(nameColIndex);
                bankExample.mRegion = c.getString(regionColIndex);
                bankExample.mCity = c.getString(cityColIndex);
                bankExample.mPhoneNumber = c.getString(phoneColIndex);
                bankExample.mAddress = c.getString(addressColIndex);
                bankExample.mLink = c.getString(emailColIndex);

                bankExample.mCurrencies = currenciesDao.getCurrencies(c.getInt(bankPosition));
                example.add(bankExample);
            } while (c.moveToNext());
        }else c.close();

        return example;
    }

    public void deleteDatabase(){
        SQLiteDatabase db = BankDatabase.getInstance().getWritableDatabase();
        db.delete(TABLE_BANKS, null, null);
        currenciesDao.deleteCurrencies();
    }

}
