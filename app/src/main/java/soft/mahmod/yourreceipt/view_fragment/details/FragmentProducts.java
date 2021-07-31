package soft.mahmod.yourreceipt.view_fragment.details;

import android.os.Bundle;

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
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.view_model.VMProductsByReceiptId;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProducts extends Fragment {
    private FragmentProductsBinding binding;
    private VMProductsByReceiptId vmProductsByReceiptId;
    private ARProducts adapter;
    private List<Products> listModel = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_products, container, false);
        vmProductsByReceiptId = new ViewModelProvider(getViewModelStore(),new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication()))
                .get(VMProductsByReceiptId.class);
        loadProducts();
        return binding.getRoot();
    }
    private void loadProducts() {
        binding.productsRecycler.setHasFixedSize(true);
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ARProducts(listModel);
        binding.productsRecycler.setAdapter(adapter);
        vmProductsByReceiptId.productsByReceiptId("59").observe(getViewLifecycleOwner(),products -> {
            if (products!=null){
                listModel.addAll(products);
                adapter.notifyDataSetChanged();
            }
        });
    }
}