package com.example.hm.convertlab.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hm.convertlab.MainActivity;
import com.example.hm.convertlab.api.ItemBank;
import com.example.hm.convertlab.api.Modell.BankResponse;
import com.example.hm.convertlab.api.HomeAdapter;
import com.example.hm.convertlab.api.Modell.Banks;
import com.example.hm.convertlab.api.MyAsyncTaskLoader;
import com.example.hm.convertlab.api.RestProvider;
import com.example.hm.convertlab.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hm on 28.01.2016.
 */
public class HomeFragment extends Fragment implements ItemBank, LoaderManager.LoaderCallbacks<BankResponse> {
    Banks mBank;
    View view;
    ProgressBar prBAr;

    private RecyclerView mHomeResyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private Handler mHandler;

    BankResponse mResponse;
    HomeAdapter mHomeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mHandler = new Handler();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(1, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        getActivity().setTitle(" Convert Lab");

        mHomeResyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        mHomeResyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(getActivity());
        mHomeResyclerView.setLayoutManager(mLayoutManager);

        prBAr = (ProgressBar) view.findViewById(R.id.progBar);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchView search =(SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                final List<Banks> filtredBanks = new ArrayList<Banks>();

                for (int i = 0; i < mResponse.mBanks.size(); i++){

                    final String textName = mResponse.mBanks.get(i).mBankName.toLowerCase();
                    final String textRegion = mResponse.mBanks.get(i).mRegion.toLowerCase();
                    final String textCity = mResponse.mBanks.get(i).mCity.toLowerCase();

                    if (textName.contains(newText) || textRegion.contains(newText) || textCity.contains(newText)){
                        filtredBanks.add( mResponse.mBanks.get(i));
                    }
                }
                mHomeAdapter.setBankInfo(filtredBanks);
                mHomeAdapter.notifyDataSetChanged();

                return true;
            }
        });

    }

    @Override
    public void setBank(Banks _bank) {
        mBank = _bank;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        DetailFragment dtFrag = DetailFragment.newInstance(mBank);
        ft.replace(R.id.fragCont, dtFrag)
                .addToBackStack(ft.getClass().getSimpleName())
                .commit();

    }

    @Override
    public Loader<BankResponse> onCreateLoader(int id, Bundle args) {
        prBAr.setVisibility(View.VISIBLE);
        return new MyAsyncTaskLoader(getActivity(), args);
    }

    @Override
    public void onLoadFinished(Loader<BankResponse> loader, final BankResponse data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                prBAr.setVisibility(View.GONE);
                mResponse = data;
                mHomeAdapter = new HomeAdapter(mResponse.mBanks);
                mHomeAdapter.setListener(HomeFragment.this);

                mHomeResyclerView.setAdapter(mHomeAdapter);
                mHomeResyclerView.scrollToPosition(data.mBanks.size() - 1);
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<BankResponse> loader) {

    }

}
