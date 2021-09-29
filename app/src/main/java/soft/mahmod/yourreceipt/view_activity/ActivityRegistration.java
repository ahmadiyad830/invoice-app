package soft.mahmod.yourreceipt.view_activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.controller.SecurityManager;
import soft.mahmod.yourreceipt.databinding.ActivityRegistrationBinding;
import soft.mahmod.yourreceipt.dialog.DialogConfirm;
import soft.mahmod.yourreceipt.dialog.DialogListener;
import soft.mahmod.yourreceipt.utils.IntentActivity;
import soft.mahmod.yourreceipt.view_model.auth.VMSettingAuth;

public class ActivityRegistration extends AppCompatActivity {
    private static final String TAG = "ActivityRegistration";
    private ActivityRegistrationBinding binding;
    private ActivityIntent intent;
    private VMSettingAuth vmSettingAuth;
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
        vmSettingAuth = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (getApplication())).get(VMSettingAuth.class);
        binding.btnHelp.setOnClickListener(v -> {
            dialogHelp();
        });

        Log.d(TAG, "onCreateshiw: "+ SecurityManager.getInectance(this).isShow());

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (intent.isConnection() && vmSettingAuth.hasCredential() && vmSettingAuth.isVerified()) {
            binding.setHasReg(true);
            intent.userSign(this);
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
        vmSettingAuth.onCleared();
        vmSettingAuth = null;

    }
}