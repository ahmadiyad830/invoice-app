package soft.mahmod.yourreceipt.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import soft.mahmod.yourreceipt.view_activity.ActivityRegistration;
import soft.mahmod.yourreceipt.view_activity.MainActivity;

public class ActivityIntent {
    private static Context mCtx;
    private static ActivityIntent instance;

    public ActivityIntent(Context context) {
        mCtx = context;
    }

    public static ActivityIntent getInstance(Context context) {
        if (instance == null){
            instance = new ActivityIntent(context);
        }
        return instance;
    }

    public void userMakeChange(Activity activity){
        Intent intent = new Intent(mCtx, MainActivity.class);
        mCtx.startActivity(intent);
        activity.finish();
    }
    public void userSignOut(Activity activity) {
        Intent intent = new Intent(mCtx, ActivityRegistration.class);
        mCtx.startActivity(intent);
        activity.finish();
    }
    public void userSignIn(Activity activity) {
        Intent intent = new Intent(mCtx, MainActivity.class);
        mCtx.startActivity(intent);
        activity.finish();
    }
}
