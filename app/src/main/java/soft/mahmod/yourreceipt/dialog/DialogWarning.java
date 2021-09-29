package soft.mahmod.yourreceipt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SecurityManager;
import soft.mahmod.yourreceipt.databinding.LayoutDialogWarningBinding;

public class DialogWarning {
    private static final String TAG = "DialogSecurity";
    private final Context context;
    private final LayoutInflater inflater;
    private final Dialog dialog;
    private String input = "";
    private SecurityManager manager;

    public DialogWarning(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        manager = SecurityManager.getInectance(context);
        dialog = new Dialog(context);
    }

    public void warningDialog() {
        if (manager.hasKey() || manager.numLaunchApp()>1) {
            return;
        }

        LayoutDialogWarningBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_dialog_warning
                , null, false);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        binding.btnDown.setOnClickListener(v -> {
            manager.setFirstLaunch(2);
            dialog.dismiss();
        });
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            dialog.cancel();
            e.printStackTrace();
        } catch (IllegalStateException exception) {
            exception.printStackTrace();
        }
    }

}
