package soft.mahmod.yourreceipt.view_fragment.add_receipt.tab_add_products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.common.Common;
import soft.mahmod.yourreceipt.databinding.FragmentAddItemBinding;
import soft.mahmod.yourreceipt.model.Products;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddProducts extends Fragment implements ARProducts.OnClickItem {
    public static final String TAG = "FragmentAddProducts";
    private FragmentAddItemBinding binding;

    private ARProducts adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ARProducts(Common.listProduct, this);
        adapter.setCreate(true);
        binding.recItem.setHasFixedSize(true);
        binding.recItem.setAdapter(adapter);
        binding.txtDeleteAll.setOnClickListener(v -> {
            if (Common.listProduct.size() > 0) {
                adapter.notifyItemRangeRemoved(0, adapter.getItemCount());
                Common.listProduct.clear();
                Common.totlaAll = 0.0;
            }
            binding.setTotalAll(0.0);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void editProduct(Products model, int position) {

    }

    @Override
    public void deleteProduct(Products model, int position) {
        double oldTotal = Double.parseDouble(binding.total.getText().toString().trim());
        double price = model.getPrice();
        double newTotal = oldTotal - price;
        binding.total.setText(String.valueOf(newTotal));
        Common.totlaAll = newTotal;
        adapter.notifyItemRemoved(position);
        Common.listProduct.remove(position);

    }

    @Override
    public void setTotalAll(double total) {
        binding.setTotalAll(total);
    }

}