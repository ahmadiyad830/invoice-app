package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ViewPager2Adapter;
import soft.mahmod.yourreceipt.databinding.ActivityDetailsBinding;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentProducts;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentReceipt;

public class ActivityDetails extends AppCompatActivity {
    private static final String TAG = "ActivityDetails";
    private ActivityDetailsBinding binding;
    private ViewPager2Adapter viewPager2Adapter;
    private Receipt receipt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        sendData();
        loadTabLayout();
        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });


    }

    private void sendData() {

    }
    private void loadTabLayout() {
        viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2Adapter.addFragment(new FragmentReceipt());
        viewPager2Adapter.addFragment(new FragmentProducts());
        binding.viewPager2.setAdapter(viewPager2Adapter);
        asd(binding.tableLayout, binding.viewPager2);
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

    private void asd(TabLayout tableLayout, ViewPager2 viewPager2) {
    }


}