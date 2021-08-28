package soft.mahmod.yourreceipt.utils;


import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class DialogConfirm {

    private AlertDialog.Builder alertDialog;
    private DialogListener dialogListener;

    public DialogConfirm(Context context, DialogListener dialogListener) {
        alertDialog = new AlertDialog.Builder(context);
        this.dialogListener = dialogListener;
    }

    public DialogConfirm createDialog(String title, String message) {
        alertDialog.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("CANCEL", (dialog, which) -> {
                    dialogListener.clickCancel(dialog);
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    dialogListener.clickOk(dialog);
                });
        return this;
    }

    public void showDialog() {
        alertDialog.show();
    }

}
