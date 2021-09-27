package soft.mahmod.yourreceipt.view_fragment.details;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARProduct;
import soft.mahmod.yourreceipt.databinding.FragmentProductsBinding;
import soft.mahmod.yourreceipt.listeners.ListenerProduct;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendReceipt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProducts extends Fragment implements ListenerProduct, DatabaseUrl {
    private static final String TAG = "FragmentProducts";
    private FragmentProductsBinding binding;
    private List<Products> listModel = new ArrayList<>();
    private VMSendReceipt vmSendReceipt;
    private ARProduct adapter;
    private DatabaseReference reference;
    private String uid;
    private VMReceipt vmReceipt;
    private Receipt receiptModel;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        vmReceipt = new ViewModelProvider(getViewModelStore(),new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(VMReceipt.class);


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

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vmSendReceipt = new ViewModelProvider(requireActivity()).get(VMSendReceipt.class);
        vmSendReceipt.getModel().observe(getViewLifecycleOwner(), receipt -> {
            if (!receipt.getError()) {
                receiptModel = receipt;
                FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery
                        (reference
                                .child("receipt")
                                .child(uid)
                                .child(receipt.getReceiptId())
                                .child("products"), Products.class).build();
                adapter = new ARProduct(options, this);
                binding.productsRecycler.setAdapter(adapter);
                adapter.startListening();
            }
            Log.d(TAG, "onViewCreated: " + receipt.toString());

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
        double oldTotal = receiptModel.getTotalAll();
        double totalRemove = product.getTotal();
        double newTotal = oldTotal - totalRemove;
        adapter.getRef(position).removeValue();
        vmReceipt.editValue(newTotal,receiptModel.getReceiptId(),"totalAll")
                .observe(getViewLifecycleOwner(),cash -> {
                    Log.d(TAG, "deleteProduct: "+cash.toString());
                });
    }
}