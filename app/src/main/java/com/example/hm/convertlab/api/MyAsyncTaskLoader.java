package com.example.hm.convertlab.api;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.hm.convertlab.MyNetworkManager;
import com.example.hm.convertlab.api.Modell.BankResponse;
import com.example.hm.convertlab.api.Modell.Banks;
import com.example.hm.convertlab.api.Modell.Currencies;
import com.example.hm.convertlab.database.dao.BankDao;

import java.util.Iterator;
import java.util.List;

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
        startHomeScreenIfDatabaseNotNull();
        startRefreshDatabase(bankDao);
        return mBankResponse;
    }

    public void startHomeScreenIfDatabaseNotNull(){
        if (list.size() != 0){
            mBankResponse.mBanks=list;
            deliverResult(mBankResponse);
        }
    }

    public void startRefreshDatabase(BankDao _bankDao){
        if (MyNetworkManager.isNetworkAvailable(getContext())) {
            mBankResponse = RestProvider.getInstance().getBankService().getBanks();
            checkChangeCurrencies(mBankResponse.mBanks, list);
            deliverResult(mBankResponse);
            _bankDao.deleteDatabase();
            _bankDao.addAllBanks(mBankResponse.mBanks);
            Log.d("Ricsi", "Loader");
        }
    }

    public void checkChangeCurrencies(List<Banks> _banks, List<Banks> _list){
        for (Banks bank : _banks)  {
            String bankId = bank.mBankID;

            for (Iterator<Banks> it = _list.iterator(); it.hasNext();){
                Banks databaseBank = it.next();
                String databaseBankId = databaseBank.mBankID;
                ///////////////////////////////////////////////////////////////
                ifEquals(bankId, databaseBankId, bank, databaseBank, it);
            }

        }
    }

    public void ifEquals(String _bankId, String _databaseBankId, Banks _bank, Banks _databaseBank, Iterator<Banks> _it){
        if (_bankId.equals(_databaseBankId)){
            List<Currencies> bankCur = _bank.mCurrencies;
            List<Currencies> databaseBankCur = _databaseBank.mCurrencies;
            for (Currencies actualBankCur : bankCur){
                String actualBankCurName = actualBankCur.CurrenciesName;
                for (Currencies actualDatabaseBankCur : databaseBankCur){
                    String actualDatabaseBankCurName = actualDatabaseBankCur.CurrenciesName;
                    if (actualBankCurName.equals(actualDatabaseBankCurName)){
                        checkChangeAsk(actualBankCur, actualDatabaseBankCur);
                        checkChangeBid(actualBankCur, actualDatabaseBankCur);
                    }
                }
            }
            _it.remove();
        }
    }

    public void checkChangeAsk(Currencies _actualBankCur, Currencies _actulDatabaseBankCur){
        if (Double.parseDouble(_actualBankCur.mAsk) > Double.parseDouble(_actulDatabaseBankCur.mAsk)) {
            _actualBankCur.mChangeAsk = 1;
            Log.d("mChangeAsk", String.valueOf(_actualBankCur.mChangeAsk));
        } else {
            if (Double.parseDouble(_actualBankCur.mAsk) == Double.parseDouble(_actulDatabaseBankCur.mAsk)){
                _actualBankCur.mChangeAsk = 0;
                Log.d("mChangeAsk", String.valueOf(_actualBankCur.mChangeAsk));
            }else _actualBankCur.mChangeAsk = 2;
            Log.d("mChangeAsk", String.valueOf(_actualBankCur.mChangeAsk));
        }
    }

    public void checkChangeBid(Currencies _actualBankCur, Currencies _actulDatabaseBankCur){
        if (Double.parseDouble(_actualBankCur.mBid) > Double.parseDouble(_actulDatabaseBankCur.mBid)) {
            _actualBankCur.mChangeBid = 1;
            Log.d("mChangeBid", String.valueOf(_actualBankCur.mChangeBid));
        } else {
            if (Double.parseDouble(_actualBankCur.mBid) == Double.parseDouble(_actulDatabaseBankCur.mBid)){
                _actualBankCur.mChangeBid = 0;
                Log.d("mChangeBid", String.valueOf(_actualBankCur.mChangeBid));
            }else _actualBankCur.mChangeBid = 2;
            Log.d("mChangeBid", String.valueOf(_actualBankCur.mChangeBid));
        }
    }
}
