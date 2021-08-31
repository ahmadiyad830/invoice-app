package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.ActivityRegistrationBinding;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.utils.IntentActivity;
import soft.mahmod.yourreceipt.view_model.user_account.VMAuthReg;

public class ActivityRegistration extends AppCompatActivity {
    private static final String TAG = "ActivityRegistration";
    private ActivityRegistrationBinding binding;
    private SessionManager manager;
    private VMAuthReg vmAuthReg;
    private NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        manager = SessionManager.getInstance(this);
        controller = Navigation.findNavController(this,R.id.nav_reg);
        controller.addOnDestinationChangedListener((controller1, destination, arguments) -> {
            binding.setName(destination.getLabel().toString());
        });
        vmAuthReg = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (getApplication())).get(VMAuthReg.class);
        binding.btnHelp.setOnClickListener(v -> {
            dialogHelp();
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (vmAuthReg.hasCredential() && vmAuthReg.isVerified()) {
            binding.setHasReg(true);
            manager.userSignIn(this);
        } else {
            binding.setHasReg(false);
            onResume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavController controller = Navigation.findNavController(this, R.id.nav_reg);
        controller.addOnDestinationChangedListener((controller1, destination, arguments) -> {
            if (destination.getLabel() != null) {
                setTitle(destination.getLabel().toString());
            }
        });
    }
    private void dialogHelp() {
        DialogConfirm confirm = new DialogConfirm(this, new DialogListener() {
            @Override
            public void clickOk(DialogInterface dialog) {
                startWhatsApp();
            }

            @Override
            public void clickCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        confirm.createDialog("Help","content with us ");
        confirm.showDialog();
    }

    private void startWhatsApp() {
        IntentActivity.startWhatsApp(this);
    }
}