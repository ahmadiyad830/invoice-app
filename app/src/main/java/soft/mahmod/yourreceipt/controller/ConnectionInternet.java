package soft.mahmod.yourreceipt.controller;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionInternet {
    protected static Context mCtx;
    protected static final String KEY_USER_ACTIVE = "user.is_ctive";
    protected static final String TAG = "SessionManager";

    private ConnectionInternet() {

    }
    public ConnectionInternet(Context context){
        mCtx = context;
    }

    public boolean isConnection() {
        ConnectivityManager cm = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                    || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }
    public static boolean isConnection(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                    || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }
}
