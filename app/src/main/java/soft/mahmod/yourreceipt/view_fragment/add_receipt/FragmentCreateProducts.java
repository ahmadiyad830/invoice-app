package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateProductsBinding;

public class FragmentCreateProducts extends Fragment {
    private static final String TAG = "FragmentCreateProducts";
    private FragmentCreateProductsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_products, container, false);
        inti();
        cleanEdit();
        binding.btnDown.setOnClickListener(v -> insert());
        return binding.getRoot();
    }

    private void inti() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void cleanEdit() {
        binding.btnClean.setOnClickListener(v -> {
            binding.edtPrice.setText("");
            binding.edtQuantity.setText("");
            binding.edtDiscount.setText("");
            binding.edtTax.setText("");
//
        });
    }

    private void insert() {


    }
}