package soft.mahmod.yourreceipt.view_activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.controller.SecurityManager;
import soft.mahmod.yourreceipt.databinding.ActivityMainBinding;
import soft.mahmod.yourreceipt.dialog.DialogConfirm;
import soft.mahmod.yourreceipt.dialog.DialogConnectionInternet;
import soft.mahmod.yourreceipt.dialog.DialogListener;
import soft.mahmod.yourreceipt.dialog.DialogWarning;
import soft.mahmod.yourreceipt.helper.IntentHelper;
import soft.mahmod.yourreceipt.helper.LocaleHelper;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.auth.VMSettingAuth;
import soft.mahmod.yourreceipt.view_model.database.VMUser;

public class MainActivity extends AppCompatActivity implements DatabaseUrl {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private VMUser vmUser;
    private VMSettingAuth vmSettingAuth;
    private ActivityIntent intent;
    private SecurityManager manager;
    private DialogConnectionInternet connectionInternet;
    private final Activity activity = this;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, LocaleHelper.ENGLISH));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        intent = ActivityIntent.getInstance(this);
        manager = SecurityManager.getInectance(this);
        vmUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(VMUser.class);
        vmSettingAuth = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(VMSettingAuth.class);

        DialogWarning dialogWarning = new DialogWarning(this, getLayoutInflater());
        connectionInternet = new DialogConnectionInternet(this,getLayoutInflater());
        connectionInternet.internetConnectionDialog();
        vmUser.getUser().observe(this, user -> {
            manager.setKeySecuirty(user.getSecurity());
            manager.setPassword(user.getPassword());
            dialogWarning.warningDialog();
        });

        if (intent.isUserActive().isActive()) {
            binding.setIsActive(intent.isUserActive().isActive());
        } else {
            vmUser.getUser().observe(this, user -> {
                Log.d(TAG, "onCreate: " + user.getMessage());
                if (!user.getError()) {
                    binding.setIsActive(user.isActive());
                    intent.setUserIsActive(user.isActive());
                } else {
                    vmSettingAuth.signOut();
                    intent.userSignOut(MainActivity.this);
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_main, menu);
        String saveLang = LocaleHelper.getLanguage(activity);
        MenuItem ar = menu.findItem(R.id.menu_ar_language);
        ar.setEnabled(!saveLang.equals(LocaleHelper.ARABIC));
        MenuItem en = menu.findItem(R.id.menu_en_language);
        en.setEnabled(!saveLang.equals(LocaleHelper.ENGLISH));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.btn_signout) {
            signOut();
            return true;
        }else if (id==R.id.menu_ar_language){
            LocaleHelper.setLocale(activity,LocaleHelper.ARABIC);
            IntentHelper.startActivityWithFinish(activity,activity.getClass());
            return true;
        }else if (id==R.id.menu_en_language){
            LocaleHelper.setLocale(activity,LocaleHelper.ENGLISH);
            IntentHelper.startActivityWithFinish(activity,activity.getClass());
            return true;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSupportActionBar(binding.linearLayout4);
        SecurityManager manager = SecurityManager.getInectance(this);
        NavController controller = Navigation.findNavController(this, R.id.nav_host);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(controller.getGraph())
                .setOpenableLayout(binding.drawer)
                .build();

        emailHeader();

        controller.addOnDestinationChangedListener((controller1, destination, arguments) -> {
            binding.setName(destination.getLabel().toString());
        });
        NavigationUI.setupWithNavController(binding.navigationView, controller);
        NavigationUI.setupWithNavController(binding.linearLayout4, controller, configuration);

        binding.fab.setOnClickListener(v -> {
            ActivityIntent.getInstance(MainActivity.this).addReceipt(MainActivity.this);
        });
        binding.txtActive.setOnClickListener(v -> {
            IntentHelper.startWhatsApp(activity);
        });
        binding.txtAnotherAccount.setOnClickListener(v -> {
            vmSettingAuth.signOut();
            manager.clean();
            intent.userSignOut(MainActivity.this);
        });
    }

    private void emailHeader() {
        View view = binding.navigationView.getHeaderView(0);
        TextView emailHeader  = view.findViewById(R.id.text_header_email);
        emailHeader.setText(vmSettingAuth.email());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        intent = null;
        getViewModelStore().clear();
        vmSettingAuth.onCleared();
        vmSettingAuth = null;
        vmUser.onCleared();
        vmUser = null;
    }

    private void signOut() {
        DialogConfirm dialog = new DialogConfirm(this);
        dialog.listenerDialog();
        dialog.setDialogListener(new DialogListener() {
            @Override
            public void clickOk(DialogInterface dialog) {
                vmSettingAuth.signOut();
                manager.clean();
                Log.d(TAG, "clickOk: " + manager.numLaunchApp());
                intent.userSignOut(MainActivity.this);
                dialog.dismiss();
            }

            @Override
            public void clickCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.createDialog("Sign out", "Are You shure");
        dialog.showDialog();
    }
}
