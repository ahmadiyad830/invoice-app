package soft.mahmod.yourreceipt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SecurityManager;
import soft.mahmod.yourreceipt.databinding.LayoutSecurityBinding;
import soft.mahmod.yourreceipt.listeners.ListenerSecurityDialog;

public class DialogSecurity implements TextWatcher {
    private static final String TAG = "DialogSecurity";
    private final Context context;
    private final LayoutInflater inflater;
    private final Dialog dialog;
    private String input = "";
    private SecurityManager manager;
    private LayoutSecurityBinding binding;
    public boolean inSetting = false;
    public DialogSecurity(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        manager = SecurityManager.getInectance(context);
        dialog = new Dialog(context);
    }

    public boolean hasKey() {
        if (manager.keySecuirty().equals("")) {
            return false;
        } else return manager.keySecuirty().length() == 4;
    }

    public boolean showDialog() {
        return manager.isShow();
    }

    private boolean isInput() {
        return input.equals(manager.keySecuirty());
    }


    public void securityDialog(ListenerSecurityDialog listener) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_security
                , null, false);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        binding.btnDown.setOnClickListener(v -> {
            listener.onOk(dialog, isInput());
            if (!isInput())
                binding.setError(getString(R.string.wrong_security_number));
        });
        binding.btnCancle.setOnClickListener(v -> {
            listener.onCancel(dialog);
        });
        binding.edtSecurity.addTextChangedListener(this);
        binding.boxDontShow.setEnabled(isInput());
        binding.boxDontShow.setChecked(!manager.isShow());
        binding.boxDontShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isInput()) {
                manager.setShow(!isChecked);
            } else {
                binding.setError(getString(R.string.wrong_security_number));
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

    private String getString(int rec) {
        return dialog.getContext().getResources().getString(rec);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        input = s.toString();
        binding.boxDontShow.setEnabled(isInput());
//        if (!isInput())
//            binding.boxDontShow.setChecked(false);
    }
}
