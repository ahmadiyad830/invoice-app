package soft.mahmod.yourreceipt.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class IntentHelper {
    public static void startActivityWithoutFinish(Context packageContext, Class<?> cls){
        Intent intent = new Intent(packageContext,cls);
        packageContext.startActivity(intent);
    }
    public static void startActivityWithFinish(Context packageContext, Class<?> cls, Activity activity){
        Intent intent = new Intent(packageContext,cls);
        packageContext.startActivity(intent);
        activity.finish();
    }
}
