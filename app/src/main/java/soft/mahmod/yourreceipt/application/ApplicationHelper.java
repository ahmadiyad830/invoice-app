package soft.mahmod.yourreceipt.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import soft.mahmod.yourreceipt.controller.LanguageApp;
import soft.mahmod.yourreceipt.controller.LocaleHelper;

public class ApplicationHelper extends Application {
    private static final String TAG = "ApplicationHelper";

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(TAG, "attachBaseContext: ");
        super.attachBaseContext(LocaleHelper.onAttach(base,"en"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleHelper.setLocale(this,"en");
    }
}
