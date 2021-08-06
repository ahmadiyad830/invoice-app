package soft.mahmod.yourreceipt.utils;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingImageUrl {
    private static final String TAG = "BindingAdapters";

    @BindingAdapter("android:imageURL")
    public static void setImageURL(ImageView imageURL , String URL){
        try {
            imageURL.setAlpha(0f);
            Picasso.get().load(URL).noFade().into(imageURL, new Callback() {
                @Override
                public void onSuccess() {
                    imageURL.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "onError: "+e.getMessage());
                    e.printStackTrace();

                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Exception: piccaso "+e.getMessage());
            e.printStackTrace();
        }
    }
}
