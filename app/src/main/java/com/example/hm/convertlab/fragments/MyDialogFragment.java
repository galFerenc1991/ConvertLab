package com.example.hm.convertlab.fragments;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hm.convertlab.R;
import com.example.hm.convertlab.api.Modell.Banks;
import com.example.hm.convertlab.custom_view.CustomView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hm on 08.02.2016.
 */
public class MyDialogFragment extends DialogFragment implements View.OnClickListener {
    private Banks bank;
    CustomView myView;
    Button btnShare;
    String fileName = "myImage";
    Bitmap bitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        bank = (Banks) getArguments().getSerializable("bank");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment,container,false);
        myView = (CustomView) view.findViewById(R.id.customView);
        btnShare = (Button) view.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
        myView.setDialogBanks(bank);
        return view;
    }

    public static MyDialogFragment newInstance(Banks _bank) {
        Bundle args = new Bundle();
        args.putSerializable("bank", _bank);

        MyDialogFragment fragment = new MyDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View v) {

        bitmap = Bitmap.createBitmap(myView.getMeasuredWidth(),
                myView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        myView.draw(canvas);
        myView.invalidate();

        File file = new File(getContext().getCacheDir(), fileName + ".png");
        FileOutputStream fOut = null;
        file.setReadable(true, false);
        try {
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/png");
        startActivity(intent);

    }
}
