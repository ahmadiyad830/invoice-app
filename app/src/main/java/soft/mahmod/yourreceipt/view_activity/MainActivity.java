package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ActivityMainBinding;
import soft.mahmod.yourreceipt.view_model.registration.VMDbUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private VMDbUser vmDbUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        vmDbUser = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(
                        getApplication()
                )
        ).get(VMDbUser.class);
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
    }
}
