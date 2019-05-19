package android.statussaver.com.statussaver.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsApps {

        private static final String PREF_NAME = "android.statussaver.com.statussaver.utils";
        private Context context;

        public SettingsApps(Context context) {
            this.context = context;
        }

        public SharedPreferences getPref() {
            return context.getSharedPreferences(PREF_NAME, 0);
        }

    public void setShowGuide(boolean value) {
        getPref().edit().putBoolean("ShowGuide", value).commit();
    }

    public boolean getShowGuide() {
        boolean value = getPref().getBoolean("ShowGuide", true);
        return value;
    }


}
