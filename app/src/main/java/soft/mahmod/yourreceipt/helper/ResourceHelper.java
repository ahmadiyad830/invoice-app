package soft.mahmod.yourreceipt.helper;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class ResourceHelper {
    public static String getString(Activity activity,@StringRes int rec) {
        Resources resources = activity.getResources();
        return resources.getString(rec);
    }
    public static Drawable getDrawable(Activity activity, @DrawableRes int rec) {
        return ContextCompat.getDrawable(activity,rec);
    }
    public static Drawable getDrawable(Activity activity, @DrawableRes int rec,@Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawable(activity.getResources(),rec,activity.getTheme());
    }
}
