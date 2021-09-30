package soft.mahmod.yourreceipt.helper;

import android.app.Activity;
import android.content.Intent;

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
}
