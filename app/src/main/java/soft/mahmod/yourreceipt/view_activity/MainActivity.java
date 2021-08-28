package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
import soft.mahmod.yourreceipt.view_model.user_account.VMAuthReg;
import soft.mahmod.yourreceipt.view_model.database.VMDbUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private VMDbUser vmDbUser;
    private VMAuthReg vmAuthReg;
    private SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        manager = SessionManager.getInstance(this);
        vmDbUser = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(
                        getApplication()
                )
        ).get(VMDbUser.class);
        vmAuthReg = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(VMAuthReg.class);
        vmDbUser.readUser();
        vmDbUser.getErrorData().observe(this,cash -> {
            if (!cash.getError()){
                vmDbUser.getData().observe(this,user -> {
                    if (user.isActive()){
                        onStart();
                    }else {
                        binding.setIsActive(false);
                    }
                });
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
        if(binding.getIsActive()){
            binding.txtActive.setOnClickListener(v -> {
                String url = "https://api.whatsapp.com/send?phone="+"+962782317354";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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
