package soft.mahmod.yourreceipt.utils;


import android.content.Context;
import android.content.DialogInterface;
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
    public AlertDialog create(){
        return alertDialog.create();
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

    public DialogConfirm listenerDialog() {
        alertDialog
                .setNegativeButton("CANCEL", (dialog, which) -> {
                    dialogListener.clickCancel(dialog);
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    dialogListener.clickOk(dialog);
                });
        return this;
    }

    public DialogConfirm defaultListener(){
        alertDialog.setPositiveButton("OK",(dialog, which) -> {
            dialog.dismiss();
        });
        return this;
    }
    public DialogConfirm setCancel(boolean canCancel) {
        alertDialog.setCancelable(canCancel);
        return this;
    }

    public DialogConfirm setIcon(@DrawableRes int resIcon) {
        alertDialog.setIcon(resIcon);
        return this;
    }

    public DialogConfirm setTitle(String title) {
        alertDialog.setTitle(title);
        return this;
    }
    public DialogConfirm setMessage(String message) {
        alertDialog.setTitle(message);
        return this;
    }

    public DialogConfirm createDialog(String title, String message) {
        alertDialog.setTitle(title)
                .setMessage(message);
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
