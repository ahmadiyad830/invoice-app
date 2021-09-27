package soft.mahmod.yourreceipt.view_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ViewPager2Adapter;
import soft.mahmod.yourreceipt.databinding.ActivityDetailsBinding;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentDetailsReceipt;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentPayment;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentProducts;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendReceipt;

public class ActivityDetails extends AppCompatActivity {
    private static final String TAG = "ActivityDetails";
    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        sendData();
        loadTabLayout();
        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        binding.btnPrint.setOnClickListener(v -> {

        });
    }

    private Receipt model;
    private void sendData() {
        VMSendReceipt vmSendReceipt = new ViewModelProvider(this).get(VMSendReceipt.class);
        model = (Receipt) getIntent().getSerializableExtra("model");
        vmSendReceipt.setModel(model);
    }

    private void loadTabLayout() {
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2Adapter.addFragment(new FragmentDetailsReceipt());
        viewPager2Adapter.addFragment(new FragmentProducts());
        viewPager2Adapter.addFragment(new FragmentPayment());
        binding.viewPager2.setAdapter(viewPager2Adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tableLayout, binding.viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText(getResources().getString(R.string.receipt));
            } else if (position == 1) {
                tab.setText(getResources().getString(R.string.products));
            } else if (position == 2) {
                tab.setText(getResources().getString(R.string.bayment));
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}