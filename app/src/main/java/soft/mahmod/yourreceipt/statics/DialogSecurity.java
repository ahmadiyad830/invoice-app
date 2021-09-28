package soft.mahmod.yourreceipt.statics;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.LayoutSecurityBinding;
import soft.mahmod.yourreceipt.listeners.ListenerSecurityDialog;

public class DialogSecurity {
    private static final String TAG = "DialogSecurity";
    private final Context context;
    private final LayoutInflater inflater;
    private final Dialog dialog;


    public DialogSecurity(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        dialog = new Dialog(context);
    }

    public void securityDialog(String key, ListenerSecurityDialog listener) {
        LayoutSecurityBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_security
                , null, false);

        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        if (!key.isEmpty()){
            binding.btnDown.setOnClickListener(v -> {
                listener.onOk(dialog, binding);
            });
        }else {
            binding.setError(dialog.getContext().getResources().getString(R.string.dont_have_number_security));
        }

        binding.btnCancle.setOnClickListener(v -> {
            listener.onCancel(dialog);
        });
        SessionManager manager = SessionManager.getInectance(dialog.getContext());
        String num = binding.edtSecurity.getText().toString().trim();
        binding.boxDontShow.setChecked(manager.isShow());
        binding.boxDontShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (manager.keySecuirty().isEmpty() && manager.keySecuirty().equals(num)) {
                manager.dontShow(isChecked);
            } else {
                binding.setError(dialog.getContext().getResources().getString(R.string.dont_have_number_security));
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
