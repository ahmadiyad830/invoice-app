package soft.mahmod.yourreceipt.view_activity;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.databinding.ActivityRegistrationBinding;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.utils.IntentActivity;
import soft.mahmod.yourreceipt.view_model.user_account.VMAuthReg;

public class ActivityRegistration extends AppCompatActivity {
    private static final String TAG = "ActivityRegistration";
    private ActivityRegistrationBinding binding;
    private ActivityIntent intent;
    private VMAuthReg vmAuthReg;
    private NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        intent = ActivityIntent.getInstance(this);
        controller = Navigation.findNavController(this, R.id.nav_reg);
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
        if (intent.isConnection() && vmAuthReg.hasCredential() && vmAuthReg.isVerified()) {
            binding.setHasReg(true);
            intent.userSignIn(this);
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
        confirm.listenerDialog();
        confirm.createDialog("Help","content with us ");
        confirm.showDialog();
    }

    private void startWhatsApp() {
        IntentActivity.startWhatsApp(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        intent = null;
        getViewModelStore().clear();
        vmAuthReg.onCleared();
        vmAuthReg = null;

    }
}