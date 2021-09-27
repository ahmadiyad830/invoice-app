package soft.mahmod.yourreceipt.controller;

import android.content.Context;

public class SessionManager {
    public static final String IBAN_SECYRTY = "iban_number";
    private Context context;
    private static SessionManager inectance;

    private SessionManager(Context context) {
        this.context = context;
    }

    public static SessionManager getInectance(Context context) {
        if (inectance == null) {
            inectance = new SessionManager(context);
        }
        return inectance;
    }

    // TODO: 9/27/2021  edit
    // TODO: 9/27/2021   remove
    // TODO: 9/27/2021   add
    // TODO: 9/27/2021
}
