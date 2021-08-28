package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateItemBinding;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.view_model.database.VMItems;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateItem extends Fragment {
    private static final String TAG = "FragmentCreateItem";
    private FragmentCreateItemBinding binding;
    private VMItems vmItems;
    private NavController controller;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmItems = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(
                        requireActivity().getApplication()
                )
        ).get(VMItems.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_item, container, false);
        binding.btnDown.setOnClickListener(v -> {
            createItem();
        });

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        binding.btnBack.setOnClickListener(v -> {
            controller.navigate(FragmentCreateItemDirections.actionFragmentCreateItemToFragmentAddReceipt());
        });
    }

    private void createItem() {
        vmItems.createItem(getModel());
        vmItems.getErrorData()
                .observe(
                        getViewLifecycleOwner(),
                        cash -> {
                            if (!cash.getError()) {
                                controller.navigate(FragmentCreateItemDirections.actionFragmentCreateItemToFragmentAddReceipt());
                            } else {
                                Log.d(TAG, "createItem: " + cash.toString());
                            }
                        }
                );
    }

    private Items getModel() {
        String itemName = binding.edtName.getText().toString().trim();
        String itemPrice = binding.edtPrice.getText().toString().trim();
        String quantity = binding.edtQuantity.getText().toString().trim();
        String itemTax = binding.edtTax.getText().toString().trim();
        String itemNote = binding.edtSubject.getText().toString().trim();
        String discount = binding.edtDiscount.getText().toString().trim();
        return new Items(itemName, itemPrice, discount, itemTax, itemNote, quantity);
    }

}