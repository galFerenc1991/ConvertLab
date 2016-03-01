package com.example.hm.convertlab;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.hm.convertlab.api.Modell.BankResponse;
import com.example.hm.convertlab.api.RestProvider;
import com.example.hm.convertlab.dao.BankDao;

/**
 * Created by hm on 22.02.2016.
 */
public class MyService extends IntentService {
    public BankResponse mBankResponse = new BankResponse();
    BankDao bankDao = new BankDao();

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (MyApplication.sIsActive)
            return;
        Log.d("Ricsi", "frissites");

        if (MyNetworkManager.isNetworkAvailable(this)){
            startRefreshDatabase();
        }
    }

    public void startRefreshDatabase(){
        mBankResponse = RestProvider.getInstance().getBankService().getBanks();
        bankDao.deleteDatabase();
        bankDao.addAllBanks(mBankResponse.mBanks);
    }

}
