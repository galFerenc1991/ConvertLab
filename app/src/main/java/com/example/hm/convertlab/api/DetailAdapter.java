package com.example.hm.convertlab.api;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hm.convertlab.api.Modell.Banks;
import com.example.hm.convertlab.R;

/**
 * Created by hm on 03.02.2016.
 */
public class DetailAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Banks mBank;
    View headerItem;
    View courrenciesItem;


    public DetailAdapter(Banks _banks){
        mBank = _banks;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == 0){
            type = 0;
        }else type = 1;
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            headerItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_card_view, parent, false);

            return new HeaderViewHolder(headerItem);
        } else {

            courrenciesItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.currencies_card_view, parent, false);
            return new CurrenciesViewHolder(courrenciesItem);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0){
            HeaderViewHolder HVH = (HeaderViewHolder)holder;
            HVH.mBankNameHeader.setText(mBank.mBankName);
            HVH.mBankAddressHeader.setText("Адрес: " + mBank.mAddress);
            HVH.mBankPhoneNumberHeader.setText("Телефон: " + mBank.mPhoneNumber);
            HVH.mBankEmailHeader.setText("e-mail: " + mBank.mLink);
            HVH.mCurrenciesNameSablon.setText("Назва валюти");
            HVH.mBidSablon.setText("Покупка/");
            HVH.mAskSablon.setText("Продажа");
        } else
        {
            setChangeCurrencies(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mBank.mCurrencies.size() + 1;
    }

    public void setChangeCurrencies(RecyclerView.ViewHolder _holder, int _position){
        CurrenciesViewHolder CVH = (CurrenciesViewHolder) _holder;
        CVH.mCourrenciesName.setText(mBank.mCurrencies.get(_position-1).mCurrenciesFullName);
        //////////
        setChangeAsk(CVH, _position);
        setChangeBid(CVH, _position);

        CVH.mAsk.setText(mBank.mCurrencies.get(_position-1).mAsk);
        CVH.mBid.setText(mBank.mCurrencies.get(_position-1).mBid);
    }

    public void setChangeAsk(CurrenciesViewHolder _CVH,int _position){
        switch (mBank.mCurrencies.get(_position - 1).mChangeAsk){
        case 2:///////UP////////
            _CVH.mAsk.setTextColor(ContextCompat.getColor(headerItem.getContext(), R.color.currencies_up_color));
            _CVH.mImageAsk.setImageResource(R.drawable.ic_green_arrow_up);
            break;
        case 0:////////DOWN/////
            _CVH.mAsk.setTextColor(ContextCompat.getColor(headerItem.getContext(), R.color.currencies_down_color));
            _CVH.mImageAsk.setImageResource(R.drawable.ic_red_arrow_down);
            break;
        case 1://///DEFAULT//////
            _CVH.mAsk.setTextColor(ContextCompat.getColor(headerItem.getContext(), R.color.text_home));
            _CVH.mImageAsk.setImageResource(android.R.color.transparent);
            break;
        }
    }

    public void setChangeBid(CurrenciesViewHolder _CVH,int _position){
        switch (mBank.mCurrencies.get(_position - 1).mChangeBid){
        case 0:///////UP////////
            _CVH.mBid.setTextColor(ContextCompat.getColor(headerItem.getContext(), R.color.currencies_up_color));
            _CVH.mImageBid.setImageResource(R.drawable.ic_green_arrow_up);
            break;
        case 1:////////DOWN/////
            _CVH.mBid.setTextColor(ContextCompat.getColor(headerItem.getContext(), R.color.currencies_down_color));
            _CVH.mImageBid.setImageResource(R.drawable.ic_red_arrow_down);
            break;
        case 2://///DEFAULT//////
            _CVH.mBid.setTextColor(ContextCompat.getColor(headerItem.getContext(), R.color.text_home));
            _CVH.mImageBid.setImageResource(android.R.color.transparent);
            break;
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView mBankNameHeader;
        public TextView mBankAddressHeader;
        public TextView mBankPhoneNumberHeader;
        public TextView mBankEmailHeader;
        public TextView mCurrenciesNameSablon;
        public TextView mBidSablon;
        public TextView mAskSablon;

        public HeaderViewHolder(View v){
            super(v);
            mBankNameHeader =          (TextView) v.findViewById(R.id.bankNameHeader);
            mBankAddressHeader =       (TextView) v.findViewById(R.id.bankAdressHeader);
            mBankPhoneNumberHeader =   (TextView) v.findViewById(R.id.bankPhoneNumberHeader);
            mBankEmailHeader =         (TextView) v.findViewById(R.id.bankEmailHeader);
            mCurrenciesNameSablon =    (TextView) v.findViewById(R.id.courrenciesNameSablon);
            mBidSablon =               (TextView) v.findViewById(R.id.bidSablon);
            mAskSablon =               (TextView) v.findViewById(R.id.askSablon);

        }

    }

    public class CurrenciesViewHolder extends RecyclerView.ViewHolder {
        public TextView mCourrenciesName;
        public TextView mBid;
        public TextView mAsk;
        public ImageView mImageAsk;
        public ImageView mImageBid;


        public CurrenciesViewHolder(View v){
            super(v);
            mCourrenciesName =   (TextView) v.findViewById(R.id.courrenciesName);
            mAsk =               (TextView) v.findViewById(R.id.ask);
            mBid =               (TextView) v.findViewById(R.id.bid);
            mImageAsk =          (ImageView) v.findViewById(R.id.askImage);
            mImageBid =          (ImageView) v.findViewById(R.id.bidImage);
        }

    }

}
