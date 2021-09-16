package soft.mahmod.yourreceipt.view_activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.databinding.ActivityMainBinding;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.utils.IntentActivity;
import soft.mahmod.yourreceipt.view_model.database.VMUser;
import soft.mahmod.yourreceipt.view_model.user_account.VMAuthReg;

public class MainActivity extends AppCompatActivity implements DatabaseUrl {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private VMUser vmUser;
    private VMAuthReg vmAuthReg;
    private ActivityIntent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        intent = ActivityIntent.getInstance(this);
        vmUser = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(
                        getApplication()
                )
        ).get(VMUser.class);
        vmAuthReg = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(VMAuthReg.class);
        if (intent.isUserActive().isActive()){
            Log.d(TAG, "onCreate: not active");
            binding.setIsActive(intent.isUserActive().isActive());
        }else {
            vmUser.getUser().observe(this, user -> {
                if (!user.getError()) {
                    binding.setIsActive(user.isActive());
                    intent.setUserIsActive(user.isActive());
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
            vmAuthReg.signOut();
            intent.userSignOut(MainActivity.this);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        intent = null;
        getViewModelStore().clear();
        vmAuthReg.onCleared();
        vmAuthReg = null;
        vmUser.onCleared();
        vmUser = null;
    }

    private void signOut() {
        DialogConfirm dialog = new DialogConfirm(this);
        dialog.listenerDialog();
        dialog.setDialogListener(new DialogListener() {
            @Override
            public void clickOk(DialogInterface dialog) {
                vmAuthReg.signOut();
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
