package soft.mahmod.yourreceipt.utils;

import android.app.Activity;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

import soft.mahmod.yourreceipt.controller.ConnectionInternet;

public class HandleNetworkConnection {
    public interface ListenerNetworkConnection {
        void onNetworkFailure();
    }
    private Handler handler = new Handler();
    private Runnable runnable;

    public void setOnNetworkConnecton(Activity activity, ListenerNetworkConnection connection) {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 500);
                    if (ConnectionInternet.isConnection(activity)) {
                        onPause();
                    } else {
                        connection.onNetworkFailure();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    public void onPause() {
        handler.removeCallbacks(runnable);
    }
}

