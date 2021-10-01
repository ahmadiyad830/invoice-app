package soft.mahmod.yourreceipt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import soft.mahmod.yourreceipt.R;

public class DialogWithView {
    public static Dialog dialogWthView(Context context, View view, boolean candel){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(candel);
        return dialog;
    }
}
