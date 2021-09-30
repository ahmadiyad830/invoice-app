package soft.mahmod.yourreceipt.view_fragment.details;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARFirebaseProduct;
import soft.mahmod.yourreceipt.databinding.FragmentCreateProductsBinding;
import soft.mahmod.yourreceipt.databinding.FragmentProductsBinding;
import soft.mahmod.yourreceipt.helper.CalculatorTax;
import soft.mahmod.yourreceipt.helper.DialogWithView;
import soft.mahmod.yourreceipt.listeners.ListenerDialogWithView;
import soft.mahmod.yourreceipt.listeners.ListenerFirebaseProduct;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendData;

public class FragmentProducts extends Fragment implements DatabaseUrl, ListenerFirebaseProduct {
    private static final String TAG = "FragmentProducts";
    private FragmentProductsBinding binding;
    private VMSendData vmSendData;
    private ARFirebaseProduct adapter;
    private DatabaseReference reference;
    private Client client;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);

        return binding.getRoot();
    }

    private void init(ARFirebaseProduct adapter) {
        binding.productsRecycler.setHasFixedSize(true);
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.productsRecycler.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vmSendData = new ViewModelProvider(requireActivity()).get(VMSendData.class);
        vmSendData.getReceipt().observe(getViewLifecycleOwner(), receipt -> {
            if (receipt != null) {
                if (!receipt.getError()) {
                    Query query = reference.child(RECEIPT)
                            .child(FirebaseAuth.getInstance().getUid())
                            .child(receipt.getReceiptId())
                            .child(PRODUCTS);
                    FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(query, Products.class)
                            .build();
                    adapter = new ARFirebaseProduct(options, this);
                    init(adapter);
                    adapter.startListening();
                }
            }
        });

        vmSendData.getClient().observe(getViewLifecycleOwner(), client1 -> {
            if (client1 != null){
                this.client = client1;
            }
        });

    }

    @Override
    public void onEditProduct(Products model, int position) {
        FragmentCreateProductsBinding layoutBinding = DataBindingUtil
                .inflate(getLayoutInflater(), R.layout.fragment_create_products, null, false);
        DecimalFormat format = new DecimalFormat("$#,##0.00");
        try {
            model.setTotal(Double.parseDouble(format.format(model.getTotal())));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        layoutBinding.setModel(model);
        Dialog dialog = DialogWithView.dialogWthView(requireContext(), layoutBinding.getRoot(), true);
        layoutBinding.btnDown.setOnClickListener(v -> {
            adapter.getRef(position)
                    .setValue(getProduct(layoutBinding));
            dialog.dismiss();
        });
        layoutBinding.btnClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private Products getProduct(FragmentCreateProductsBinding binding) {
        Products model = new Products();
        String name = binding.edtName.getText().toString().trim();
        double price;
        try {
            price = Double.parseDouble(binding.edtPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            price = 0;
        }
        double quantity;
        try {
            quantity = Double.parseDouble(binding.edtQuantity.getText().toString().trim());
        } catch (NumberFormatException e) {
            quantity = 0;
        }
        double discount;
        try {
            discount = Double.parseDouble(binding.edtDiscount.getText().toString().trim());
        } catch (NumberFormatException e) {
            discount = 0;
        }
        double tax;
        try {
            tax = Double.parseDouble(binding.edtTax.getText().toString().trim());
        } catch (NumberFormatException e) {
            tax = 0;
        }
        double total;
        try {
            total = Double.parseDouble(binding.edtTotal.getText().toString().trim());
        } catch (NumberFormatException e) {
            total = 0;
        }
        model.setName(name);
        model.setTax(tax);
        model.setPrice(price);
        model.setQuantity(quantity);
        model.setDiscount(discount);
        model.setTotal(total);
        model.setTotal(calculator(model));
        return model;
    }

    private double calculator(Products model) {
        if (!client.isTaxExempt()) {
            return CalculatorTax.withTax(model);
        } else {
            return CalculatorTax.withoutTax(model);
        }
    }
}