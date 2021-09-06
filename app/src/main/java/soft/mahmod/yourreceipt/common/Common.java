package soft.mahmod.yourreceipt.common;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import soft.mahmod.yourreceipt.R;

public class Common {
    private static final String TAG = "Common";

    //    ahmad iyad\vivo 1904\وحدة تخزين الجهاز الداخلية\Download
//    /storage/emulated/0/Your Receipt/file_name.pdf
//    4299 2000 3916 6879
    public static String getPath(Context context) {
        File dir = new File(
                Environment.getExternalStorageDirectory()
                        + File.separator
                        + context.getResources().getString(R.string.app_name)
                        + File.separator
        );

        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath() + File.separator;
    }
}
