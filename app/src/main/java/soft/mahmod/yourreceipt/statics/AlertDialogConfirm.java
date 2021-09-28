package soft.mahmod.yourreceipt.statics;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.LayoutSecurityBinding;
import soft.mahmod.yourreceipt.listeners.ListenerSecurityDialog;
import soft.mahmod.yourreceipt.view_activity.MainActivity;

public class AlertDialogConfirm {
    private final Context context;
    private final LayoutInflater inflater;
    private final Dialog dialog;


    public AlertDialogConfirm(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        dialog = new Dialog(context);
    }

    public void securityDialog(ListenerSecurityDialog listener) {
        LayoutSecurityBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_security
                , null, false);

        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        binding.btnDown.setOnClickListener(v -> {
            listener.onOk(dialog, binding);
        });
        binding.btnCancle.setOnClickListener(v -> {
            listener.onCancel(dialog);
        });
        binding.boxDontShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SessionManager manager = SessionManager.getInectance(dialog.getContext());
            manager.dontShow(isChecked);
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
