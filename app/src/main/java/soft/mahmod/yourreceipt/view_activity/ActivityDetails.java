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
//        TODO delete just used to test send with view mode
        vmSendReceipt.getModel().observe(this, receipt1 -> {

        });
    }

    private void loadTabLayout() {
        binding.setToolbar(getResources().getString(R.string.receipt));
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2Adapter.addFragment(new FragmentDetailsReceipt());
        if (
                model.getPayment().getTypePayment().equals(getResources().getString(R.string.bayment))
                ||  model.getPayment().getTypePayment().equals(getResources().getString(R.string.debt))
        )
            viewPager2Adapter.addFragment(new FragmentPayment());
        viewPager2Adapter.addFragment(new FragmentProducts());
        binding.viewPager2.setAdapter(viewPager2Adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tableLayout,
                binding.viewPager2, (tab, position) -> {
            switch (position) {
                case 1:
                    tab.setText(getResources().getString(R.string.bayment));
                    break;
                case 2:
                    tab.setText(getResources().getString(R.string.products));
                    break;
                case 0:
                default:
                    tab.setText(binding.getToolbar());
                    break;


            }
            Log.d(TAG, "loadTabLayout: "+position);
        });
        tabLayoutMediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}