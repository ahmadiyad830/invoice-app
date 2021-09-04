package soft.mahmod.yourreceipt.utils;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;

public class DialogConfirm {

    private AlertDialog.Builder alertDialog;
    private DialogListener dialogListener;
    private int resContainer;


    public DialogConfirm(Context context, DialogListener dialogListener) {
        alertDialog = new AlertDialog.Builder(context);
        this.dialogListener = dialogListener;
    }

    public DialogConfirm(Context context) {
        alertDialog = new AlertDialog.Builder(context);
    }

    public void addView(View recLayout) {

        alertDialog.setView(recLayout);
    }

    public Context context() {
        return alertDialog.getContext();
    }

    public void listenerDialog() {
        alertDialog
                .setNegativeButton("CANCEL", (dialog, which) -> {
                    dialogListener.clickCancel(dialog);
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    dialogListener.clickOk(dialog);
                });
    }

    public DialogConfirm addIcon(@DrawableRes int resIcon) {
        alertDialog.setIcon(resIcon);
        return this;
    }

    public DialogConfirm createDialog(String title, String message) {
        alertDialog.setTitle(title)
                .setMessage(message)
                .setCancelable(false);
        return this;
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void showDialog() {
        alertDialog.show();
    }

    public void setResContainer(int resContainer) {
        this.resContainer = resContainer;
    }

    public ViewGroup getResContainer() {
        return alertDialog.create().findViewById(resContainer);
    }
}
