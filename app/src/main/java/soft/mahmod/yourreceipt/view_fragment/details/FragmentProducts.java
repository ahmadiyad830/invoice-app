package soft.mahmod.yourreceipt.view_fragment.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.databinding.FragmentProductsBinding;
import soft.mahmod.yourreceipt.listeners.OnClickDeleteItemListener;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.view_model.VMProductsByReceiptId;
import soft.mahmod.yourreceipt.view_model.ui.VMSendReceipt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProducts extends Fragment implements OnClickDeleteItemListener<Products> {
    private FragmentProductsBinding binding;
    private VMProductsByReceiptId vmProductsByReceiptId;
    private ARProducts adapter;
    private List<Products> listModel = new ArrayList<>();
    private VMSendReceipt vmSendReceipt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        vmProductsByReceiptId = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication()))
                .get(VMProductsByReceiptId.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vmSendReceipt = new ViewModelProvider(requireActivity()).get(VMSendReceipt.class);
        vmSendReceipt.getModel().observe(getViewLifecycleOwner(), receipt -> {
            if (receipt != null) {
                loadProducts(receipt.getReceiptId());
            }
        });
    }

    private void loadProducts(String id) {
        binding.productsRecycler.setHasFixedSize(true);
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ARProducts(listModel,this);
        binding.productsRecycler.setAdapter(adapter);
        vmProductsByReceiptId.productsByReceiptId(id).observe(getViewLifecycleOwner(), products -> {
            int old = listModel.size();
            if (products != null) {
                listModel.addAll(products);
                adapter.notifyItemRangeInserted(old, products.size());
            }
        });
    }

    @Override
    public void onClickDeleteItem(Products model,int position) {

    }

    @Override
    public void onClickItem(Products model) {

    }
}