package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.ActivityMainBinding;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.view_model.VMUserConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private VMUserConnection connection;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        connection = new ViewModelProvider
                (this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(VMUserConnection.class);
        manager = SessionManager.getInstance(this);
        if (manager == null) {
            finish();
        }else {
            onStart();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        connection.userConnection(manager.getUser().getEmail()).observe(this, cash -> {
            if (cash != null) {
                if (cash.getError()) {
                    manager.userSignOut(this);
                    Toast.makeText(this, cash.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    onResume();
                }
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        NavController controller = Navigation.findNavController(this, R.id.main_container);
        NavigationUI.setupWithNavController(binding.mainBottom, controller);
        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityAddReceipt.class);
            startActivity(intent);
        });

    }

    //    private void createReceipt() {
//        Receipt model = new Receipt(manager.getUser().getUserId(), "android", "asdfasdf",
//                "ahmad iyad", "12341234", "1234");
//        vmCreateReceipt.createReceipt(model).observe(this, receipt -> {
//            int oldSize = listModel.size();
//            if (!receipt.getError()) {
//                listModel.add(listModel.size() - 1, model);
//                adapter.notifyItemRangeInserted(oldSize, listModel.size());
//            }
//        });
//    }
}