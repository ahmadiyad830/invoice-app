package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.ActivityMainBinding;
import soft.mahmod.yourreceipt.utils.IntentActivity;
import soft.mahmod.yourreceipt.view_model.user_account.VMAuthReg;
import soft.mahmod.yourreceipt.view_model.database.VMUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private VMUser vmUser;
    private VMAuthReg vmAuthReg;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        manager = SessionManager.getInstance(this);

        vmUser = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(
                        getApplication()
                )
        ).get(VMUser.class);
        vmAuthReg = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(VMAuthReg.class);

        vmUser.getUser().observe(this, user -> {
            if (!user.getError()) {
                binding.setIsActive(user.isActive());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.setIsActive(true);
        NavController controller = Navigation.findNavController(this, R.id.main_container);
        NavigationUI.setupWithNavController(binding.mainBottom, controller);
        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityAddReceipt.class);
            startActivity(intent);
        });
        if (binding.getIsActive()) {
            binding.txtActive.setOnClickListener(v -> {
                IntentActivity.startWhatsApp(this);
            });
            binding.txtAnotherAccount.setOnClickListener(v -> {
                vmAuthReg.signOut();
                manager.userSignOut(MainActivity.this);
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();
    }
}
