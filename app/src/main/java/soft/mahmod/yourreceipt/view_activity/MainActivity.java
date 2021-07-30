package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARReceipt;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.ActivityMainBinding;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.view_model.VMCreateReceipt;
import soft.mahmod.yourreceipt.view_model.VMReceiptByEmail;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }


    @Override
    protected void onStart() {
        super.onStart();
        NavController controller = Navigation.findNavController(this, R.id.main_container);
        NavigationUI.setupWithNavController(binding.mainBottom, controller);
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