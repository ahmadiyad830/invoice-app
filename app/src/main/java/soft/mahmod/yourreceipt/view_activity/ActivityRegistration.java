package soft.mahmod.yourreceipt.view_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.helper.IntentHelper;
import soft.mahmod.yourreceipt.helper.LocaleHelper;
import soft.mahmod.yourreceipt.databinding.ActivityRegistrationBinding;
import soft.mahmod.yourreceipt.view_model.auth.VMSettingAuth;


public class ActivityRegistration extends AppCompatActivity  {
    private static final String TAG = "ActivityRegistration";
    private ActivityRegistrationBinding binding;
    private ActivityIntent intent;
    private VMSettingAuth vmSettingAuth;
    private NavController controller;
    private Activity context = this;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        intent = ActivityIntent.getInstance(this);
        controller = Navigation.findNavController(this, R.id.nav_reg);

        setSupportActionBar(binding.linearLayout4);

        controller.addOnDestinationChangedListener((controller1, destination, arguments) -> {
            binding.setName(destination.getLabel().toString());
        });
        vmSettingAuth = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (getApplication())).get(VMSettingAuth.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_registration_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        if (id == R.id.menu_ar_language ) {
            LocaleHelper.setLocale(context,"ar");
            IntentHelper.startActivityWithFinish(context, ActivityRegistration.class);
            return true;
        }else if(id == R.id.menu_en_language){
            LocaleHelper.setLocale(context,"en");
            IntentHelper.startActivityWithFinish(context, ActivityRegistration.class);
            return true;
        }
        return true;
    }

    private void intentI() {
        Intent intent = new Intent(this, ActivityRegistration.class);
        startActivity(intent);
        finish();
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