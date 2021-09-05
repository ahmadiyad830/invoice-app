package soft.mahmod.yourreceipt.repository.ui;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;

public class HelpMessage extends DialogConfirm implements DialogListener {
    private Context context;

    public HelpMessage(Context context) {
        super(context);
        listenerDialog();
        setTitle();
    }
    private void setTitle(){
        super.setTitle(context.getResources().getString(R.string.app_name));
    }
    @Override
    public DialogConfirm setTitle(String title) {
        return super.setTitle("title");
    }

    @Override
    public DialogConfirm listenerDialog() {
        return this;
    }

    @Override
    public void clickOk(DialogInterface dialog) {
        setMessage("");
        dialog.dismiss();
    }

    @Override
    public void clickCancel(DialogInterface dialog) {
        setMessage("");
        dialog.dismiss();
    }
}
