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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayoutMediator;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ViewPager2Adapter;
import soft.mahmod.yourreceipt.databinding.FragmentMainAddItemBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendClient;


public class FragmentMainAddItem extends Fragment {

    private static final String TAG = "FragmentMainAddItem";
    private FragmentMainAddItemBinding binding;
    private NavController controller;
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
        controller = Navigation.findNavController(view);
        getPassArgs();

        binding.btnNext.setOnClickListener(v -> {
            passReceipt();
        });
        loadTabLayout();
    }

    private Client getPassArgs() {
        FragmentMainAddItemArgs args = FragmentMainAddItemArgs.fromBundle(getArguments());
        return args.getClientToAddItem();
    }

    private void sendClientModel() {
        VMSendClient vmSendClient = new ViewModelProvider(requireActivity()).get(VMSendClient.class);
        vmSendClient.setModel(getPassArgs());
    }

    private void passReceipt() {
        FragmentMainAddItemDirections.ActionMainAddItemToAddReceipt
                receiptToReceipt = FragmentMainAddItemDirections.actionMainAddItemToAddReceipt();
        receiptToReceipt.setReceiptToAddReceipt(getReceipt(getPassArgs()));
        controller.navigate(receiptToReceipt);
    }

    private void loadTabLayout() {
        sendClientModel();
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(requireActivity());
        viewPager2Adapter.addFragment(new FragmentAddItem());
        viewPager2Adapter.addFragment(new FragmentAddProducts());
        binding.viewPager2.setAdapter(viewPager2Adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tableLayout,
                binding.viewPager2, (tab, position) -> {
            switch (position) {
                case 1:
                    tab.setText(getResources().getString(R.string.products));
                    break;
                case 0:
                default:
                    tab.setText(getResources().getString(R.string.items));
                    break;
            }
            Log.d(TAG, "loadTabLayout: " + position);
        });
        tabLayoutMediator.attach();
    }

    private Receipt getReceipt(Client clientToAddItem) {
        Receipt receipt = new Receipt();
        receipt.setClientName(clientToAddItem.getName());
        receipt.setClientId(clientToAddItem.getClientId());
        receipt.setClientPhone(clientToAddItem.getPhone());
        if (FragmentAddProducts.listProduct.size() > 0)
            receipt.setProducts(FragmentAddProducts.listProduct);
        return receipt;
    }

}