package soft.mahmod.yourreceipt.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
}
