package soft.mahmod.yourreceipt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ConnectionInternet;
import soft.mahmod.yourreceipt.databinding.LayoutDialogWarningBinding;

public class DialogConnectionInternet {

    private static final String TAG = "DialogSecurity";
    private final Context context;
    private final LayoutInflater inflater;
    private final Dialog dialog;
    private ConnectionInternet internet;

    public DialogConnectionInternet(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        this.dialog = new Dialog(context);
        internet = new ConnectionInternet(context);
    }

    public void internetConnectionDialog() {
        if (internet.isConnection()) {
            return;
        }

        LayoutDialogWarningBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_dialog_warning
                , null, false);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        binding.setWarning(context.getResources().getString(R.string.network_conecction));
        binding.btnDown.setOnClickListener(v -> {
            if (internet.isConnection()){
                dialog.dismiss();
            }else {
                binding.setWarning(context.getResources().getString(R.string.network_conecction));
            }

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
