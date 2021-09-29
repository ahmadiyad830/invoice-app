package soft.mahmod.yourreceipt.listeners;

import android.app.Activity;
import android.app.Dialog;
import android.widget.CompoundButton;

import soft.mahmod.yourreceipt.databinding.LayoutSecurityBinding;

public interface ListenerSecurityDialog {
    void onOk(Dialog dialog, boolean notWrong);
    void onCancel(Dialog dialog);


}
