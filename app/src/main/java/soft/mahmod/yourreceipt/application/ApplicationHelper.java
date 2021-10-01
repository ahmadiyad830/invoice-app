package soft.mahmod.yourreceipt.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import soft.mahmod.yourreceipt.helper.LocaleHelper;

public class ApplicationHelper extends Application {
    private static final String TAG = "ApplicationHelper";

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(TAG, "attachBaseContext: ");
        super.attachBaseContext(LocaleHelper.onAttach(base,LocaleHelper.ENGLISH));
    }
}
