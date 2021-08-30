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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProducts extends Fragment implements OnClickDeleteItemListener<Products> {
    private FragmentProductsBinding binding;
    private ARProducts adapter;
    private List<Products> listModel = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadProducts(String id) {
        binding.productsRecycler.setHasFixedSize(true);
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ARProducts(listModel, new ARProducts.OnClickItem() {
            @Override
            public void clickProduct(Products model, int position) {

            }

            @Override
            public void deleteProduct(Products model, int position) {

            }
        });
        binding.productsRecycler.setAdapter(adapter);
    }

    @Override
    public void onClickDeleteItem(Products model,int position) {

    }

    @Override
    public void onClickItem(Products model) {

    }
}