package com.statussaver.chamiappslk.statussaver.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.statussaver.com.statussaver.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class openWhatsappFragment extends DialogFragment implements View.OnClickListener {

    private Context context;
    private Button btnyes, btnNo;
    private ProgressBar progres_ad;
   // private AdView mAdView;
   // private LinearLayout adView;
    private AdView adView;
    private LinearLayout adContainer;


    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //progressDialog = new CustomProgressDialog(getActivity());
        View view = inflater.inflate(R.layout.dialogfragment_whatsappopen, container, false);
        btnNo = view.findViewById(R.id.btnNo);
        btnyes = view.findViewById(R.id.btnyes);
       // mAdView = view.findViewById(R.id.adView);
        adContainer =view.findViewById(R.id.banner_container);
        progres_ad = view.findViewById(R.id.progres_ad);

        btnyes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
       // MobileAds.initialize(getActivity(), getString(R.string.admob_ad_id));
        loadAd();

        return view;
    }

    private void loadAd() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.setVisibility(View.GONE);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                mAdView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//            }
//        });
//        mAdView.loadAd(adRequest);

//
        adView = new AdView(getActivity(), getResources().getString(R.string.fb_banner_ad), AdSize.BANNER_HEIGHT_50);
        adContainer.addView(adView);
        adView.loadAd();


    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnyes:
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                startActivity(launchIntent);
                dismiss();
                break;

            case R.id.btnNo:
                dismiss();
                break;

        }
    }
}
