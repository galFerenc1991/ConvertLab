package com.example.hm.convertlab.api;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hm.convertlab.api.Modell.Banks;
import com.example.hm.convertlab.R;

import java.util.List;

/**
 * Created by hm on 27.01.2016.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<Banks> mBanksInfo;
    private ItemBank mListener;

    public HomeAdapter(List<Banks> _banks){
        mBanksInfo = _banks;
    }
    public void setBankInfo(List<Banks> _bankList){
        mBanksInfo = _bankList;
    }

    public void setListener(ItemBank _listener) {
        mListener = _listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_card_view, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Banks bank = mBanksInfo.get(position);
        holder.mBankName.setText(bank.mBankName);
        holder.mRegionName.setText(bank.mRegion);
        holder.mCityName.setText(bank.mCity);
        holder.mAdress.setText(bank.mAddress);
        holder.mPhoneNumber.setText(bank.mPhoneNumber);
        holder.ivCall.setOnClickListener(holder);
        holder.ivCall.setTag(position);
        holder.ivLink.setOnClickListener(holder);
        holder.ivLink.setTag(position);
        holder.ivMap.setOnClickListener(holder);
        holder.ivMap.setTag(position);
        holder.ivNext.setOnClickListener(holder);
        holder.ivNext.setTag(position);
        holder.cardView.setOnClickListener(holder);
        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mBanksInfo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mBankName;
        public TextView mRegionName;
        public TextView mCityName;
        public TextView mPhoneNumber;
        public TextView mAdress;
        public ImageView ivCall;
        public ImageView ivMap;
        public ImageView ivLink;
        public ImageView ivNext;
        public CardView cardView;


        public MyViewHolder(View v) {
            super(v);
            mBankName =   (TextView) v.findViewById(R.id.bankName);
            mRegionName = (TextView) v.findViewById(R.id.regionName);
            mCityName =   (TextView) v.findViewById(R.id.cityName);
            mAdress =     (TextView) v.findViewById(R.id.adress);
            mPhoneNumber =(TextView) v.findViewById(R.id.phoneNumber);
            ivCall =      (ImageView) v.findViewById(R.id.btnPhone);
            ivLink =      (ImageView) v.findViewById(R.id.btnLink);
            ivMap =       (ImageView) v.findViewById(R.id.btnMap);
            ivNext =      (ImageView) v.findViewById(R.id.btnNext);
            cardView =    (CardView)  v.findViewById(R.id.card_view);

        }

        @Override
        public void onClick(View v) {
            int _position = (int) v.getTag();
            Banks bank = mBanksInfo.get(_position);
            Intent intent;

            switch (v.getId()) {
                case R.id.btnPhone:
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + bank.mPhoneNumber));
                    v.getContext().startActivity(intent);
                    break;
                case R.id.btnLink:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bank.mLink));
                    v.getContext().startActivity(intent);
                    break;
                case R.id.btnMap:
                    String check = bank.mAddress + "," + bank.mCity + "," + bank.mRegion;
                    String map = "http://maps.google.co.in/maps?q=" + check;
                     intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                    v.getContext().startActivity(intent);
                    break;
                case R.id.btnNext:
                case R.id.card_view:
                    mListener.setBank(bank);
                    break;
            }
        }
    }
}
