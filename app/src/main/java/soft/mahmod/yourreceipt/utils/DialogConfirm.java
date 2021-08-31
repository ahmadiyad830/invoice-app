package soft.mahmod.yourreceipt.utils;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

public class DialogConfirm {

    private AlertDialog.Builder alertDialog;
    private DialogListener dialogListener;
    private int resContainer;

    public void setResContainer(int resContainer) {
        this.resContainer = resContainer;
    }

    public ViewGroup getResContainer() {
        return alertDialog.create().findViewById(resContainer);
    }

    public DialogConfirm(Context context, DialogListener dialogListener) {
        alertDialog = new AlertDialog.Builder(context);
        this.dialogListener = dialogListener;
    }
    public void addView(View recLayout){

        alertDialog.setView(recLayout);
    }
    public Context context (){
       return alertDialog.getContext();
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
