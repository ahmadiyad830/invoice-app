package soft.mahmod.yourreceipt.utils;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HandleTimeCount {
    private String EVENT_DATE_TIME = "2021-12-31 10:30:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private TextView tv_time;
    private Handler handler = new Handler();
    private Runnable runnable;
    private final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());


    public String getDate() {
        return date;
    }

    public HandleTimeCount() {
    }

    public void setTv_time(TextView tv_time) {
        this.tv_time = tv_time;
    }


    public void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    String formatTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    tv_time.setText(formatTime);
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

