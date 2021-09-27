package soft.mahmod.yourreceipt.statics;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.LayoutSecurityBinding;
import soft.mahmod.yourreceipt.listeners.ListenerSecurityDialog;
import soft.mahmod.yourreceipt.view_model.database.VMStore;

public class AlertDialogConfirm {
    public static void securityDialog(LayoutInflater inflate, Context context , ListenerSecurityDialog listener) {
        Dialog dialog = new Dialog(context);
        LayoutSecurityBinding binding = DataBindingUtil.inflate(inflate, R.layout.layout_security
                , null, false);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        binding.btnDown.setOnClickListener(v -> {
            listener.onOk(dialog,binding);
        });
        binding.boxDontShow.setOnCheckedChangeListener(listener::dontShowAgain);
        dialog.show();

    }
}
