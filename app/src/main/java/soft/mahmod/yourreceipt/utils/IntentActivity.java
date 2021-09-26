package soft.mahmod.yourreceipt.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

public class IntentActivity {
    public static final int REQUEST_CAMERA = 0x82317354;
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
    public static void openCamera(Activity activity){
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
    }
}
