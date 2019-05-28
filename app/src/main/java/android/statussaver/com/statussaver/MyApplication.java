package android.statussaver.com.statussaver;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.onesignal.OneSignal;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
