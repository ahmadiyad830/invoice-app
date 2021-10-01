package soft.mahmod.yourreceipt.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentHelper {
    public static void startActivityWithoutFinish(Activity activity, Class<?> cls){
        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
    }
    public static void startActivityWithFinish(Activity activity,Class<?> cls){
        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
        activity.finish();
    }
    public static void startWhatsApp(Activity activity){
        String url = "https://api.whatsapp.com/send?phone=" + "+962782317354";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }
}
