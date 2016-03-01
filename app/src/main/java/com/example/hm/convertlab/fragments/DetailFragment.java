package com.example.hm.convertlab.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.hm.convertlab.api.DetailAdapter;
import com.example.hm.convertlab.api.Modell.Banks;
import com.example.hm.convertlab.MainActivity;
import com.example.hm.convertlab.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by hm on 03.02.2016.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {
    private FloatingActionMenu fabMenu;
    private FloatingActionButton mFABMap;
    private FloatingActionButton mFABLink;
    private FloatingActionButton mFABCall;

    private Banks mBank;
    private DetailAdapter mDetailsAdapter;

    private RecyclerView mDetailRecyclerView;
    private RecyclerView.LayoutManager mDetailLayoutManager;

    private MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mBank = (Banks) getArguments().getSerializable("bank");
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.menuCall:
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mBank.mPhoneNumber));
                v.getContext().startActivity(intent);
                break;
            case R.id.menuLink:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mBank.mLink));
                v.getContext().startActivity(intent);
                break;
            case R.id.menuMap:
                String check = mBank.mAddress + "," + mBank.mCity + "," + mBank.mRegion;
                String map = "http://maps.google.co.in/maps?q=" + check;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                v.getContext().startActivity(intent);
                break;

        }
    }

    public static DetailFragment newInstance(Banks _bank) {

        Bundle args = new Bundle();
        args.putSerializable("bank", _bank);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();

        mActivity.setTitle(mBank.mBankName);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity. getSupportActionBar().setDisplayShowHomeEnabled(true);
        mActivity.getTopToolBar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.canvas_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.canvas:
                MyDialogFragment myDialogFragment =  MyDialogFragment.newInstance(mBank);
                myDialogFragment.show(getActivity().getSupportFragmentManager(),"Dialog Fragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);

        mDetailRecyclerView =(RecyclerView) view.findViewById(R.id.detail_recycler_view);
        mDetailRecyclerView.setHasFixedSize(true);

        mDetailLayoutManager = new LinearLayoutManager(getActivity());
        mDetailRecyclerView.setLayoutManager(mDetailLayoutManager);

        mDetailsAdapter = new DetailAdapter(mBank);
        mDetailRecyclerView.setAdapter(mDetailsAdapter);

        findFabUI(view);

        return view;
    }

    public void findFabUI (View view){
        fabMenu = (FloatingActionMenu) view.findViewById(R.id.menu);
        mFABMap = (FloatingActionButton) view.findViewById(R.id.menuMap);
        mFABLink = (FloatingActionButton) view.findViewById(R.id.menuLink);
        mFABCall = (FloatingActionButton) view.findViewById(R.id.menuCall);

        fabMenu.setClosedOnTouchOutside(true);
        mFABMap.setOnClickListener(this);
        mFABCall.setOnClickListener(this);
        mFABLink.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }
}
