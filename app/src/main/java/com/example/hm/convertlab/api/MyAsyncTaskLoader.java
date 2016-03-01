package com.example.hm.convertlab.api;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.hm.convertlab.api.Modell.BankResponse;
import com.example.hm.convertlab.api.Modell.Banks;
import com.example.hm.convertlab.api.Modell.Currencies;
import com.example.hm.convertlab.dao.BankDao;

import java.util.Iterator;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by hm on 09.02.2016.
 */
public class MyAsyncTaskLoader extends AsyncTaskLoader<BankResponse> {

    private BankResponse mBankResponse = new BankResponse();
    List<Banks> list;

    public MyAsyncTaskLoader(Context context, Bundle args) {
        super(context);

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mBankResponse.mBanks.size() != 0) {
            deliverResult(mBankResponse);
        } else {
            forceLoad();
        }
    }

    @Override
    public BankResponse loadInBackground() {
        BankDao bankDao = new BankDao();

        list = bankDao.getBank();

        if (list.size() != 0){
            mBankResponse.mBanks=list;
            deliverResult(mBankResponse);
        }

        try {
            mBankResponse = RestProvider.getInstance().getBankService().getBanks();
            checkChangeCurrencies(mBankResponse.mBanks, list);
            deliverResult(mBankResponse);
            bankDao.deleteDatabase();
            bankDao.addAllBanks(mBankResponse.mBanks);
            Log.d("Ricsi", "Loader");

        } catch (RetrofitError e) {
            e.printStackTrace();
        }

        return mBankResponse;
    }

    public void checkChangeCurrencies(List<Banks> _banks, List<Banks> _list){
        for (Banks bank : _banks)  {
            String bankId = bank.mBankID;

            for (Iterator<Banks> it = _list.iterator(); it.hasNext();){
                Banks databaseBank = it.next();
                String databaseBankId = databaseBank.mBankID;
                if (bankId.equals(databaseBankId)){
                    List<Currencies> bankCur = bank.mCurrencies;
                    List<Currencies> databaseBankCur = databaseBank.mCurrencies;
                    for (Currencies actualBankCur : bankCur){
                        String actualBankCurName = actualBankCur.CurrenciesName;
                        for (Currencies actualDatabaseBankCur : databaseBankCur){
                            String actualDatabaseBankCurName = actualDatabaseBankCur.CurrenciesName;
                            if (actualBankCurName.equals(actualDatabaseBankCurName)){
                                if (Double.parseDouble(actualBankCur.mAsk) > Double.parseDouble(actualDatabaseBankCur.mAsk)) {
                                    actualBankCur.mChangeAsk = 1;
                                    Log.d("mChangeAsk", String.valueOf(actualBankCur.mChangeAsk));
                                } else {
                                    if (Double.parseDouble(actualBankCur.mAsk) == Double.parseDouble(actualDatabaseBankCur.mAsk)){
                                        actualBankCur.mChangeAsk = 0;
                                        Log.d("mChangeAsk", String.valueOf(actualBankCur.mChangeAsk));
                                    }else actualBankCur.mChangeAsk = 2;
                                    Log.d("mChangeAsk", String.valueOf(actualBankCur.mChangeAsk));
                                }
                                //////////////////////////////////////////////////////////////////////////////////////////////////////////
                                if (Double.parseDouble(actualBankCur.mBid) > Double.parseDouble(actualDatabaseBankCur.mBid)) {
                                    actualBankCur.mChangeBid = 1;
                                    Log.d("mChangeBid", String.valueOf(actualBankCur.mChangeBid));
                                } else {
                                    if (Double.parseDouble(actualBankCur.mBid) == Double.parseDouble(actualDatabaseBankCur.mBid)){
                                        actualBankCur.mChangeBid = 0;
                                        Log.d("mChangeBid", String.valueOf(actualBankCur.mChangeBid));
                                    }else actualBankCur.mChangeBid = 2;
                                    Log.d("mChangeBid", String.valueOf(actualBankCur.mChangeBid));
                                }
                            }
                        }
                    }
                it.remove();
                }
            }

        }
    }
}
