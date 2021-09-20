package soft.mahmod.yourreceipt.view_fragment.create;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateProductsBinding;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.view_fragment.add_receipt.tab_add_products.FragmentAddProducts;

public class FragmentCreateProducts extends Fragment implements TextWatcher {
    private static final String TAG = "FragmentCreateProducts";
    private FragmentCreateProductsBinding binding;
    private NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_products, container, false);

        binding.btnDown.setOnClickListener(v -> {
            FragmentAddProducts.listProduct.add(model());
            requireActivity().onBackPressed();
        });
        binding.edtPrice.addTextChangedListener(this);
        binding.edtDiscount.addTextChangedListener(this);
        binding.edtTax.addTextChangedListener(this);
        binding.edtQuantity.addTextChangedListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
    }


    private double total(double price, double discount, double quantity, double tax, boolean isClientTaxNoReg) {
        if (isClientTaxNoReg) {
            return ((price - discount) * (100 / tax)) * quantity;
        } else return (price - discount) * quantity;
    }

    private Products model() {
        Products model = new Products();
        double price = Double.parseDouble(binding.edtPrice.getText().toString().trim());
        double quantity = Double.parseDouble(binding.edtQuantity.getText().toString().trim());
        double discount = Double.parseDouble(binding.edtDiscount.getText().toString().trim());
        double tax = Double.parseDouble(binding.edtTax.getText().toString().trim());
        String name = binding.edtName.getText().toString().trim();
        String note = binding.edtNote.getText().toString().trim();
        model.setPrice(price);
        model.setQuantity(quantity);
        model.setDiscount(discount);
        model.setTax(tax);
        model.setName(name);
        model.setNotes(note);
        model.setTotal(total(price, discount, quantity, tax, model.isTaxClientNoReg()));
        Log.d(TAG, "onCreateView: " + model.getTotal());
        return model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        //Ignore
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Ignore
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0)
            s.append("0");
    }
}