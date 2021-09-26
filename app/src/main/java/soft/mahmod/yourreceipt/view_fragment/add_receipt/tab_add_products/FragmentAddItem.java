package soft.mahmod.yourreceipt.view_fragment.add_receipt.tab_add_products;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARItems;
import soft.mahmod.yourreceipt.common.Common;
import soft.mahmod.yourreceipt.databinding.FragmentAddProductsBinding;
import soft.mahmod.yourreceipt.databinding.FragmentMainItemsBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddItem extends Fragment implements DatabaseUrl, TextWatcher, ARItems.OnCLickItem, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FragmentAddItem";
    private FragmentMainItemsBinding binding;
    private Client client;
    private Query query;
    private ARItems adapter;
    private String[] sortItems = {"name", "price", "quantity"};
    private String key = sortItems[0];
    private FirebaseRecyclerOptions<Items> options;
    private DatabaseReference reference;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference()
                .child(ITEMS + FirebaseAuth.getInstance().getUid());
        withoutSearch();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_items, container, false);
        binding.setInDialog(false);
        binding.setIsCreateReceipt(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VMSendClient vmSendClient = new ViewModelProvider(requireActivity()).get(VMSendClient.class);
        vmSendClient.getModel().observe(getViewLifecycleOwner(), client -> {
            if (client != null)
                this.client = client;
            Log.d(TAG, "onViewCreated: "+client.toString());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.recyclerItemsView.setHasFixedSize(true);
        binding.recyclerItemsView.setAdapter(adapter);
        spinnerInit();
        binding.btnClean.setOnClickListener(v -> {
            binding.textSearch.setText("");
        });
        binding.textSearch.addTextChangedListener(this);
        adapter.startListening();
    }


    private void spinnerInit() {
        binding.spinnerSortList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_style, sortItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSortList.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        key = (String) parent.getItemAtPosition(position);
        Log.d(TAG, "onItemSelected: " + key);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        key = (String) parent.getItemAtPosition(0);
    }


    private ARItems searchNumber(double search) {
        query = reference;
        options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(query.orderByChild(key).startAt(search).endAt(search + "\uf8ff"), Items.class)
                .build();
        adapter = new ARItems(options, this);
        adapter.startListening();
        return adapter;
    }

    private ARItems search(String search) {
        query = reference;
        options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(query.orderByChild(key).startAt(search).endAt(search + "\uf8ff"), Items.class)
                .build();
        adapter = new ARItems(options, this);
        adapter.startListening();
        return adapter;
    }

    private ARItems withoutSearch() {
        options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reference, Items.class)
                .build();
        adapter = new ARItems(options, this);
        adapter.startListening();
        return adapter;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String search = s.toString().trim();
        if (!search.isEmpty()){
            if (!key.equals(sortItems[0])) {
                try {
                    binding.recyclerItemsView.setAdapter(searchNumber(Double.parseDouble(search)));
                }catch (NumberFormatException exception){
                    Toast.makeText(requireContext(), getResources().getString(R.string.error_input_charcter), Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.recyclerItemsView.setAdapter(search(search));
            }
            binding.setHasValue(true);
        }else {
            binding.recyclerItemsView.setAdapter(withoutSearch());
        }
        binding.setHasValue(!search.isEmpty());
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void clickItem(Products model, Items itemModel, int position) {
//        FragmentAddProducts.listProduct.add(model);
        loadDialogCreateProduct(model,itemModel);
    }


    private void loadDialogCreateProduct(Products model, Items itemModel) {
        Dialog dialog = new Dialog(requireContext());
        FragmentAddProductsBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_add_products
                , null, false);
        binding.setIsTaxNoReg(client.isTaxRegNo());
        binding.setModel(model);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        binding.btnDown.setOnClickListener(v -> {
            Products products = getProduct(binding);
            products.setItemQuantity(itemModel.getQuantity());
            products.setItemId(itemModel.getItemId());
            products.setName(model.getName());
            Common.listProduct.add(products);
            totalAll();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void totalAll() {
        double total = 0.0;
        for (int i = 0; i < Common.listProduct.size(); i++) {
            total = total + Common.listProduct.get(i).getTotal();
        }
        Common.totlaAll = total;
    }


    private Products getProduct(FragmentAddProductsBinding binding) {
        Products model = new Products();
        double price;
        try {
            price = Double.parseDouble(binding.edtPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            price = 0;
        }
        double quantity ;
        try {
            quantity = Double.parseDouble(binding.edtQuantity.getText().toString().trim());
        } catch (NumberFormatException e) {
            quantity = 0;
        }
        double discount ;
        try {
            discount = Double.parseDouble(binding.edtDiscount.getText().toString().trim());
        } catch (NumberFormatException e) {
            discount = 0;
        }
        double tax ;
        try {
            tax = Double.parseDouble(binding.edtTax.getText().toString().trim());
        } catch (NumberFormatException e) {
            tax = 0;
        }
        model.setPrice(price);
        model.setQuantity(quantity);
        model.setDiscount(discount);
        if (client.isTaxRegNo()) {
            model.setTax(tax);
            model.setTotal(withTax(model));
        }
        model.setTotal(withoutTax(model));
        return model;
    }

    private double withTax(Products model) {
        // FIXME: 9/20/2021 Add Tax
        double price = model.getPrice() - model.getDiscount();
        return price;
    }

    private double withoutTax(Products model) {
        double price = model.getPrice() - model.getDiscount();
        return price * model.getQuantity();
    }
}