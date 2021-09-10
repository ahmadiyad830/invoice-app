package soft.mahmod.yourreceipt.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.view_activity.ActivityRegistration;
import soft.mahmod.yourreceipt.view_activity.MainActivity;

public class ActivityIntent extends ConnectionInternet{
    private static Context mCtx;
    private static ActivityIntent instance;

    private final SharedPreferences sharedPreferences;

    public ActivityIntent(Context context) {
        super(context);
        mCtx = context;
        sharedPreferences = mCtx.getSharedPreferences(KEY_USER_ACTIVE, Context.MODE_PRIVATE);
    }

    public static ActivityIntent getInstance(Context context) {
        if (instance == null){
            instance = new ActivityIntent(context);
        }
        return instance;
    }

    @Override
    public boolean isConnection() {
        return super.isConnection();
    }

    public void userMakeChange(Activity activity){
        Intent intent = new Intent(mCtx, MainActivity.class);
        mCtx.startActivity(intent);
        activity.finish();
    }
    public void userSignOut(Activity activity) {
        Intent intent = new Intent(mCtx, ActivityRegistration.class);
        mCtx.startActivity(intent);
        clearUser();
        activity.finish();
    }
    public void userSignIn(Activity activity) {
        Intent intent = new Intent(mCtx, MainActivity.class);
        mCtx.startActivity(intent);
        activity.finish();
    }
    public User isUserActive() {
        return new User(sharedPreferences.getBoolean(KEY_USER_ACTIVE, false));
    }
    public void setUserIsActive(boolean isActive){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_USER_ACTIVE,isActive);
        editor.apply();
    }
    public void clearUser() {
        sharedPreferences.edit().clear().apply();
    }
}
