package soft.mahmod.yourreceipt.listeners;

import android.app.Dialog;
import android.widget.CompoundButton;

import soft.mahmod.yourreceipt.databinding.LayoutSecurityBinding;

public interface ListenerSecurityDialog {
    void onOk(Dialog dialog, LayoutSecurityBinding binding);
    void dontShowAgain(CompoundButton buttonView, boolean show);
}
