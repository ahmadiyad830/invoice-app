package soft.mahmod.yourreceipt.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.listeners.SimpleDialogListener;

public class SimpleDialog {
    public static void simpleDialog(Context context, @StringRes int recTitle, @StringRes int recMessage
            , @DrawableRes int iconId, SimpleDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        Resources resources = context.getResources();
        builder.setTitle(resources.getString(recTitle))
                .setMessage(resources.getString(recMessage))
                .setIcon(iconId)
                .setPositiveButton(resources.getString(R.string.ok), (dialog, which) -> {
                    listener.clickOk(dialog);
                })
                .setNegativeButton(resources.getString(R.string.cancel), (dialog, which) -> {
                    dialog.dismiss();
                });
        builder.show();
    }

    public static AlertDialog simpleDialogWihtView(Context context, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        return builder.show();
    }
}
