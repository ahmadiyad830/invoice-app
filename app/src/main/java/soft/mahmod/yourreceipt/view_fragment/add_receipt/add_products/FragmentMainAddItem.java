package soft.mahmod.yourreceipt.view_fragment.add_receipt.add_products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ViewPager2Adapter;
import soft.mahmod.yourreceipt.databinding.FragmentMainAddItemBinding;
import soft.mahmod.yourreceipt.databinding.FragmentMainItemsBinding;
import soft.mahmod.yourreceipt.view_fragment.add_receipt.FragmentAddItem;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentDetailsReceipt;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentPayment;
import soft.mahmod.yourreceipt.view_fragment.details.FragmentProducts;


public class FragmentMainAddItem extends Fragment {

    private static final String TAG = "FragmentMainAddItem";
    private FragmentMainAddItemBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_add_item, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadTabLayout();
    }

    private void loadTabLayout() {
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(requireActivity());
        viewPager2Adapter.addFragment(new FragmentProducts());
        viewPager2Adapter.addFragment(new FragmentAddItem());
        binding.viewPager2.setAdapter(viewPager2Adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tableLayout,
                binding.viewPager2, (tab, position) -> {
            switch (position) {
                case 1:
                    tab.setText(getResources().getString(R.string.bayment));
                    break;
                case 0:
                default:
                    tab.setText("binding.getToolbar()");
                    break;
            }
            Log.d(TAG, "loadTabLayout: " + position);
        });
        tabLayoutMediator.attach();
    }
}