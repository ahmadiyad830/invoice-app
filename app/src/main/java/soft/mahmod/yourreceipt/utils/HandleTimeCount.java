package soft.mahmod.yourreceipt.utils;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HandleTimeCount {
    private String tv_time;
    private Handler handler = new Handler();
    private Runnable runnable;
    private final String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());


    public String getDate() {
        return date;
    }

    public HandleTimeCount() {

    }

    public String getTv_time() {
        return tv_time;
    }

    public void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    String formatTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    tv_time = formatTime;
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

