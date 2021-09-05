package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateProductsBinding;
import soft.mahmod.yourreceipt.model.Products;

public class FragmentCreateProducts extends Fragment {
    private static final String TAG = "FragmentCreateProducts";
    private FragmentCreateProductsBinding binding;
    private NavController controller;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_products, container, false);
        inti();
        cleanEdit();
        binding.btnDown.setOnClickListener(v ->{
            Products model = new Products();
            try {
                double price  =Double.parseDouble(binding.edtPrice.getText().toString().trim());
                model.setProductsPrice(price);
            } catch (NumberFormatException e) {
                model.setProductsPrice(0.0);
                e.printStackTrace();
            }
            try {
                double quantity  =Double.parseDouble(binding.edtQuantity.getText().toString().trim());
                model.setProductsQuantity(quantity);
            } catch (NumberFormatException e) {
                model.setProductsQuantity(0.0);
                e.printStackTrace();
            }
            try {
                double discount  =Double.parseDouble(binding.edtDiscount.getText().toString().trim());
                model.setProductsQuantity(discount);
            } catch (NumberFormatException e) {
                model.setProductsQuantity(0.0);
                e.printStackTrace();
            }
            try {
                double tax  =Double.parseDouble(binding.edtTax.getText().toString().trim());
                model.setProductsQuantity(tax);
            } catch (NumberFormatException e) {
                model.setProductsQuantity(0.0);
                e.printStackTrace();
            }
            String name = binding.edtName.getText().toString().trim();
            model.setItemName(name);
            String note = binding.edtNote.getText().toString().trim();
            model.setNotes(note);
            FragmentAddItem.listProduct.add(model);
            requireActivity().onBackPressed();
        });
        return binding.getRoot();
    }

    private void inti() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        FragmentCreateProductsArgs argsProduct = FragmentCreateProductsArgs.fromBundle(getArguments());
        if (getArguments()!=null){
            binding.setModel(argsProduct.getArgsProduct());
        }
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
}