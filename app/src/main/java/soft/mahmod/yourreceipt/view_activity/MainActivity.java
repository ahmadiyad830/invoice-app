package soft.mahmod.yourreceipt.view_activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import soft.mahmod.yourreceipt.databinding.ActivityMainBinding;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.utils.IntentActivity;
import soft.mahmod.yourreceipt.view_model.auth.SettingAuth;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;
import soft.mahmod.yourreceipt.view_model.database.VMUser;

public class MainActivity extends AppCompatActivity implements DatabaseUrl {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private VMUser vmUser;
    private SettingAuth vmSettingAuth;
    private ActivityIntent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        intent = ActivityIntent.getInstance(this);
        vmUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(VMUser.class);
        vmSettingAuth = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(SettingAuth.class);


        if (intent.isUserActive().isActive()) {
            Log.d(TAG, "onCreate: not active");
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.btn_signout) {
            signOut();
            return true;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSupportActionBar(binding.linearLayout4);
        NavController controller = Navigation.findNavController(this, R.id.nav_host);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(controller.getGraph())
                .setOpenableLayout(binding.drawer)
                .build();

        controller.addOnDestinationChangedListener((controller1, destination, arguments) -> {
            binding.setName(destination.getLabel().toString());

        });
        NavigationUI.setupWithNavController(binding.navigationView, controller);
        NavigationUI.setupWithNavController(binding.linearLayout4, controller, configuration);

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityAddReceipt.class);
            startActivity(intent);
        });
        binding.txtActive.setOnClickListener(v -> {
            IntentActivity.startWhatsApp(this);
        });
        binding.txtAnotherAccount.setOnClickListener(v -> {
            vmSettingAuth.signOut();
            intent.userSignOut(MainActivity.this);
        });
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
