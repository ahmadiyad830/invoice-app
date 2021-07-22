package soft.mahmod.yourreceipt.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.view_activity.ActivityRegistration;
import soft.mahmod.yourreceipt.view_activity.MainActivity;

public class SessionManager {
    public static final String SHARED_PREF_NAME = "user_token";


    public static final String KEY_USER_NAME = "username";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STORE_NAME = "store name";
    public static final String KEY_STORE_ADDRESS = "store address";
    public static final String KEY_STORE_PHONE = "store phone";
    private static final String TAG = "SessionManager";

    private static Context mCtx;
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;

    protected SessionManager(Context context) {
        mCtx = context;
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void userSignIn(User model) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, model.getEmail());
        editor.putString(KEY_USER_ID, model.getUserId());
        editor.putString(KEY_STORE_NAME, model.getStoreName());
        editor.putString(KEY_STORE_ADDRESS, model.getStoreAddress());
        editor.putString(KEY_STORE_PHONE, model.getPhoneNum());
        editor.apply();
        Intent intent = new Intent(mCtx, MainActivity.class);
        mCtx.startActivity(intent);

    }

    public boolean isLogedin() {
//        shuld use token
        return sharedPreferences.getString(KEY_USER_ID, null) != null;
    }

    public User getUserId(String message, boolean error, int code) {
        return new User(message, error, code,
                sharedPreferences.getString(KEY_USER_ID, null)
        );
    }

    public void userSignOut(Activity activity) {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(mCtx, ActivityRegistration.class);
        mCtx.startActivity(intent);
        activity.finish();
    }

    public void setUser(User model) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, model.getEmail());
        editor.putString(KEY_USER_ID, model.getUserId());
        editor.putString(KEY_PASSWORD, model.getPassword());
        editor.putString(KEY_USER_NAME, model.getPhoneNum());
        editor.putString(KEY_STORE_NAME, model.getStoreName());
        editor.putString(KEY_STORE_ADDRESS, model.getStoreAddress());
        editor.apply();
    }
    public User getUser(String message, boolean error, int code) {
        if (!error){
            return new User(message, error, code,
                    sharedPreferences.getString(KEY_EMAIL, ""),
                    sharedPreferences.getString(KEY_PASSWORD, ""),
                    sharedPreferences.getString(KEY_STORE_NAME, ""),
                    sharedPreferences.getString(KEY_STORE_PHONE, ""),
                    sharedPreferences.getString(KEY_STORE_ADDRESS, ""),
                    sharedPreferences.getString(KEY_USER_ID, "")
            );
        }else return new User(message,error,code);
    }
}
