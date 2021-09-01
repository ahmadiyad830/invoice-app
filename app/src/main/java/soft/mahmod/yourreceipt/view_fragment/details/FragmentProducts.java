package soft.mahmod.yourreceipt.view_fragment.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.adapter.firebase.ARProduct;
import soft.mahmod.yourreceipt.databinding.FragmentProductsBinding;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendReceipt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProducts extends Fragment implements ARProduct.OnClickItem , DatabaseUrl {
    private static final String TAG = "FragmentProducts";
    private FragmentProductsBinding binding;
    private List<Products> listModel = new ArrayList<>();
    private VMSendReceipt vmSendReceipt;
    private ARProduct adapter;
    private DatabaseReference reference;
    private String uid;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference(RECEIPT);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.productsRecycler.setHasFixedSize(true);
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
//        adapter = new ARProduct(listModel, this);
        binding.productsRecycler.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vmSendReceipt = new ViewModelProvider(requireActivity()).get(VMSendReceipt.class);
        vmSendReceipt.getModel().observe(getViewLifecycleOwner(), receipt -> {
            FirebaseRecyclerOptions<Products> productsBuilder = new FirebaseRecyclerOptions.Builder<Products>().setQuery
                    (reference.child(RECEIPT + uid+"/itemId"), Products.class).build();
        });
    }

    private void loadProducts(String id) {

    }


    @Override
    public void editProduct(Products model, int position) {

    }

    @Override
    public void deleteProduct(Products model, int position) {

    }

    @Override
    public void addProduct(Products model) {

    }
}