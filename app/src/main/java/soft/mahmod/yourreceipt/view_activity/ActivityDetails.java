package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayoutMediator;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ViewPager2Adapter;
import soft.mahmod.yourreceipt.databinding.ActivityDetailsBinding;
import soft.mahmod.yourreceipt.listeners.OnSendData;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentProducts;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentReceipt;
import soft.mahmod.yourreceipt.view_model.ui.VMSendReceipt;

public class ActivityDetails extends AppCompatActivity {
    private static final String TAG = "ActivityDetails";
    private ActivityDetailsBinding binding;
    private ViewPager2Adapter viewPager2Adapter;
    private Receipt receipt;
    private VMSendReceipt vmSendReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        sendData();
        loadTabLayout();


    }

    private void sendData() {
        vmSendReceipt = new ViewModelProvider(this).get(VMSendReceipt.class);
        Receipt model = (Receipt) getIntent().getSerializableExtra("model");
        vmSendReceipt.setModel(model);
        vmSendReceipt.getModel().observe(this,receipt1 -> {
            Log.d(TAG, "sendData: "+receipt1.toString());
        });
    }
    private void loadTabLayout() {
        viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2Adapter.addFragment(new FragmentReceipt());
        viewPager2Adapter.addFragment(new FragmentProducts());
        binding.viewPager2.setAdapter(viewPager2Adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tableLayout,
                binding.viewPager2, (tab, position) -> {
            switch (position) {
                case 1:
                    tab.setText("Products");
                    break;
                case 0:
                default:
                    tab.setText("Receipt");
                    break;

            }
        });
        tabLayoutMediator.attach();
    }


}