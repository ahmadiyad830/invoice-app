package soft.mahmod.yourreceipt.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SecurityManager {
    public static final String NUMBER_SECYRTY = "iban_number";
    public static final String SECURITY_FILE = "file securtiy";
    public static final String PASSWORD = "password";
    public static final String DONT_SHOW_BOX = "dont show again";
    public static final String FIRST_LAUNCH_APP = "first launch app";
    public static final String WARNING_ADD_KEY_SECURITY = "warnin add dialog security";
    public static final String[] KEYS_LOCATION_SHOW_KEY_SECURITY =
            {"receipt", "client", "items", "setting"
                    , "add receipt", "details", "remove", "update receipt",};
    private Context context;
    private static SecurityManager inectance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SecurityManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SECURITY_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SecurityManager getInectance(Context context) {
        if (inectance == null) {
            inectance = new SecurityManager(context);
        }
        return inectance;
    }

    public String keySecuirty() {
        return sharedPreferences.getString(NUMBER_SECYRTY, "");
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public String password() {
       return sharedPreferences.getString(PASSWORD, "");
    }

    public void setKeySecuirty(String keySecuirty) {
        editor.putString(NUMBER_SECYRTY, keySecuirty);
        editor.apply();
    }

    public void setShow(boolean isShow) {
        editor.putBoolean(DONT_SHOW_BOX, isShow);
        editor.apply();
    }

    public boolean isShow() {
        return sharedPreferences.getBoolean(DONT_SHOW_BOX, true);
    }

    public boolean hasKey() {
        return keySecuirty() != null && keySecuirty().length() == 4;
    }

    public boolean firstLaunch() {
        return sharedPreferences.getInt(FIRST_LAUNCH_APP, 1) == 1;
    }
    public int numLaunchApp(){
        return sharedPreferences.getInt(FIRST_LAUNCH_APP,1);
    }
    public void setFirstLaunch(int numStart) {
        if (Integer.MAX_VALUE == numStart)
            numStart = 2;
        editor.putInt(FIRST_LAUNCH_APP, numStart);
        editor.apply();
    }

    public void clean() {
        editor.clear().commit();
    }

}
