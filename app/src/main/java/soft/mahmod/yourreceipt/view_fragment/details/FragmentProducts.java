package soft.mahmod.yourreceipt.view_fragment.details;

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

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.databinding.FragmentProductsBinding;
import soft.mahmod.yourreceipt.listeners.ListenerProduct;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendReceipt;

public class FragmentProducts extends Fragment implements ListenerProduct, DatabaseUrl {
    private static final String TAG = "FragmentProducts";
    private FragmentProductsBinding binding;
    private List<Products> listModel = new ArrayList<>();
    private ARProducts arProduct;
    private VMSendReceipt vmSendReceipt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        arProduct = new ARProducts(listModel,this);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.productsRecycler.setHasFixedSize(true);
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vmSendReceipt = new ViewModelProvider(requireActivity()).get(VMSendReceipt.class);
        vmSendReceipt.getModel().observe(getViewLifecycleOwner(), receipt -> {
            if (!receipt.getError()) {
                try {
                    listModel.addAll(receipt.getProducts());
                    binding.setEmptyList(listModel.isEmpty());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arProduct.notifyItemRangeRemoved(0, listModel.size());
                binding.productsRecycler.setAdapter(arProduct);

            }
        });

    }

    @Override
    public void onClick(Products model) {

    }

    @Override
    public void onEdit(Products model, int position) {

    }

    @Override
    public void onDelete(Products product, int position) {

    }
}