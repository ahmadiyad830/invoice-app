package soft.mahmod.yourreceipt.view_fragment.add_receipt.tab_add_products;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import soft.mahmod.yourreceipt.databinding.LayoutDialogCreateItemBinding;
import soft.mahmod.yourreceipt.listeners.ListenerItems;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.database.VMItems;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendClient;


public class FragmentAddItem extends Fragment implements DatabaseUrl, TextWatcher, ListenerItems, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FragmentAddItem";
    private FragmentMainItemsBinding binding;
    private Client client;
    private Query query;
    private ARItems adapter;
    private String[] sortItems = {"name", "price", "quantity"};
    private String key = sortItems[0];
    private FirebaseRecyclerOptions<Items> options;
    private DatabaseReference reference;
    private VMItems vmItems;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmItems = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMItems.class);
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
        });
        binding.btnAdd.setText(getResources().getString(R.string.speed_add));
        binding.btnAdd.setOnClickListener(v -> {
            dialogCreateItem();
        });
    }

    private void dialogCreateItem() {
        Dialog dialog = new Dialog(requireContext());
        LayoutDialogCreateItemBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_dialog_create_item
                , null, false);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.custom_back_icon));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        binding.btnDown.setOnClickListener(v -> {
            vmItems.postItem(getItem(binding)).observe(getViewLifecycleOwner(), cash -> {
                Log.d(TAG, "dialogCreateItem: " + cash.toString());
            });
            dialog.dismiss();
        });
        dialog.show();
    }

    private Items getItem(LayoutDialogCreateItemBinding binding) {
        Items items = new Items();
        String name = binding.edtName.getText().toString().trim();
        double price, quantity, tax;
        try {
            price = Double.parseDouble(binding.edtPrice.getText().toString().trim());
        } catch (Exception e) {
            price = 0;
        }
        try {
            quantity = Double.parseDouble(binding.edtQuantity.getText().toString().trim());
        } catch (NumberFormatException e) {
            quantity = 0;
        }
        try {
            tax = Double.parseDouble(binding.edtTax.getText().toString().trim());
        } catch (NumberFormatException e) {
            tax = 0;
        }
        items.setName(name);
        items.setPrice(price);
        items.setQuantity(quantity);
        items.setTax(tax);
        return items;
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

    private void loadDialogCreateProduct(Products model, Items itemModel) {
        Dialog dialog = new Dialog(requireContext());
        FragmentAddProductsBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_add_products
                , null, false);
        Log.d(TAG, "loadDialogCreateProduct: " + client.isTaxExempt());
        binding.setIsTaxNoReg(client.isTaxExempt());
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
        Common.setTotalAll(total);
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
        if (!client.isTaxExempt()) {
            model.setTax(tax);
            model.setTotal(withTax(model));
        }else {
            model.setTotal(withoutTax(model));
        }
        return model;
    }

    private double withTax(Products model) {
        double price = model.getPrice() - model.getDiscount();
        double tax = model.getTax();
        double total = (price * tax) + price;
        String sim = "( " + price * tax + " )" + "+" + price + "=" + total;
        Log.d(TAG, "withTax: " + sim);
        return total * model.getQuantity();
    }

    private double withoutTax(Products model) {
        double price = model.getPrice() - model.getDiscount();
        return price * model.getQuantity();
    }

    @Override
    public void onClick(Products products, Items model) {
        if (model.getQuantity() > 0) {
            loadDialogCreateProduct(products, model);
        } else {
            loadDialogZeroQuantity(model);
        }

    }

    private void loadDialogZeroQuantity(Items model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        EditText editText = new EditText(builder.getContext());
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(editText);
        builder.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> {
            double quantity = Double.parseDouble(editText.getText().toString().trim());
            vmItems.updatesQuantity(model.getItemId(), quantity).observe(getViewLifecycleOwner(), cash -> {
                Log.d(TAG, "loadDialogZeroQuantity: " + cash.toString());
            });
        }).setNegativeButton(getResources().getString(R.string.cancel), (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setMessage(getResources().getString(R.string.increase_quntity));
        builder.show();
    }

    @Override
    public void onDelete(Items mode, int position) {

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
        binding.setEmptyTextSearch(!search.isEmpty());
        if (!search.isEmpty()) {
            if (!key.equals(sortItems[0])) {
                try {
                    binding.recyclerItemsView.setAdapter(searchNumber(Double.parseDouble(search)));
                } catch (NumberFormatException exception) {
                    Toast.makeText(requireContext(), getResources().getString(R.string.error_input_charcter), Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.recyclerItemsView.setAdapter(search(search));
            }
        } else {
            binding.recyclerItemsView.setAdapter(withoutSearch());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}