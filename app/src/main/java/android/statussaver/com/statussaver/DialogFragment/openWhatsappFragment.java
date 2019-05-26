package android.statussaver.com.statussaver.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.statussaver.com.statussaver.R;
import android.statussaver.com.statussaver.activities.MainActivity;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.ads.AudienceNetworkAds.TAG;

public class openWhatsappFragment extends DialogFragment implements View.OnClickListener {

    private Context context;
    private Button btnyes, btnNo;
    private ProgressBar progres_ad;
    private AdView mAdView;


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
        mAdView = view.findViewById(R.id.adView);
        progres_ad = view.findViewById(R.id.progres_ad);

        btnyes.setOnClickListener(this);
        btnNo.setOnClickListener(this);

        loadAd();

        return view;
    }

    private void loadAd() {

        MobileAds.initialize(getActivity(), getString(R.string.admob_ad_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        progres_ad.setVisibility(View.VISIBLE);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                progres_ad.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        mAdView.loadAd(adRequest);


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
