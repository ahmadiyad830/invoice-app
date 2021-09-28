package soft.mahmod.yourreceipt.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String IBAN_SECYRTY = "iban_number";
    public static final String SECURITY_FILE = "file securtiy";
    public static final String DONT_SHOW_BOX = "dont show again";
    private Context context;
    private static SessionManager inectance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SECURITY_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SessionManager getInectance(Context context) {
        if (inectance == null) {
            inectance = new SessionManager(context);
        }
        return inectance;
    }

    public void dontShow(boolean isShow){
        editor.putBoolean(DONT_SHOW_BOX,isShow);
        editor.apply();
    }
    public boolean isShow(){
        return sharedPreferences.getBoolean(DONT_SHOW_BOX,true);
    }

    // TODO: 9/27/2021  edit
    // TODO: 9/27/2021  remove
    // TODO: 9/27/2021  add
    // TODO: 9/27/2021  forget number
}
