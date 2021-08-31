package soft.mahmod.yourreceipt.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentActivity {
    private final Activity activity;
    private final Context context;
    private Intent intent;
    private Class<?> aClass;

    public IntentActivity(Context context, Class<?> cls) {
        this.context = context;
        this.activity = (Activity) context;
        aClass = cls;
        setIntent();
    }

    public Intent getIntent() {
        return intent;
    }

    private void setIntent() {
        this.intent = new Intent(context, activity.getClass());

    }

    public void startActivity() {
        context.startActivity(getIntent());
    }

    public void finish() {
        activity.finish();
    }

    public static void startWhatsApp(Activity activity){
        String url = "https://api.whatsapp.com/send?phone=" + "+962782317354";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }
}
