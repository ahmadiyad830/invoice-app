package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARClients;
import soft.mahmod.yourreceipt.adapter.ARItems;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.conditions.catch_add_receipt.RulesAddReceipt;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.databinding.FragmentCreateProductsBinding;
import soft.mahmod.yourreceipt.databinding.LayoutClientBinding;
import soft.mahmod.yourreceipt.databinding.LayoutItemsBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_activity.MainActivity;
import soft.mahmod.yourreceipt.view_model.add_receipt.VMRulesAddReceipt;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;

public class FragmentAddReceipt extends Fragment implements
        View.OnClickListener
        , ARItems.OnCLickItem, ARClients.OnClickClient
        , DatabaseUrl, ARProducts.OnClickItem {

    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    private HandleTimeCount handleTimeCount;
    private VMReceipt vmReceipt;
    private ARItems adapter;
    private ARClients adapterClient;
    private final List<String> listItemID = new ArrayList<>();
    private final List<Products> listProduct = new ArrayList<>();
    private ARProducts adapterProduct;
    private int sizeProduct = 0;
    private String clientId;
    private NavController controller;
    private VMRulesAddReceipt vmRulesAddReceipt;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        vmReceipt = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMReceipt.class);
        vmRulesAddReceipt = new ViewModelProvider(this).get(VMRulesAddReceipt.class);
        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reference.child(ITEMS + FirebaseAuth.getInstance().getUid()), Items.class)
                .build();
        FirebaseRecyclerOptions<Client> optionsClient = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(reference.child(CLIENT + FirebaseAuth.getInstance().getUid()), Client.class)
                .build();
        adapter = new ARItems(options, this);
        adapterClient = new ARClients(optionsClient, this);
        handleTimeCount = new HandleTimeCount();
        adapterProduct = new ARProducts(listProduct, this);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_receipt, container, false);
        init();
        binding.setHasItem(false);
        handleTimeCount.setTv_time(binding.txtTime);
        handleTimeCount.countDownStart();
        binding.setDate(handleTimeCount.getDate());

        binding.switchPrint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.setChecked(isChecked);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
    }

    private void init() {
        binding.itemsRec.setHasFixedSize(true);
        binding.itemsRec.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.itemsRec.setAdapter(adapterProduct);
    }

    private void setReceipt() {
        vmRulesAddReceipt.sizeListProduct(listProduct.size());
        vmRulesAddReceipt.getErrorData().observe(getViewLifecycleOwner(), cash -> {
            Log.d(TAG, "setReceipt: " + cash.toString());
        });
        vmReceipt.setReceipt(getReceipt());
        vmReceipt.getErrorData().observe(
                getViewLifecycleOwner(),
                cash -> {
                    if (!cash.getError()) {
                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                }
        );
    }

    private Receipt getReceipt() {
        Receipt model = new Receipt();
        model.setSubject(binding.edtSubject.getText().toString().trim());
        model.setReceiptDate("time: " + binding.txtTime.getText().toString().trim() + " date: " + handleTimeCount.getDate());
        try {
            model.setTotalAll(Double.parseDouble(binding.edtTotalAll.getText().toString().trim()));
        } catch (NumberFormatException e) {
            model.setTotalAll(0.0);
            e.printStackTrace();
        }
        try {
            model.setClientPhone(Integer.parseInt(binding.edtClientPhone.getText().toString().trim()));
        } catch (NumberFormatException e) {
            model.setClientPhone(0);
            e.printStackTrace();
        }
        model.setItemId(listItemID);
        // FIXME: 8/30/2021 client id not name
        model.setClientId(getClientId());
        model.setClientName(binding.edtClientName.getText().toString().trim());
        binding.switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.setType(isChecked ? "take" : "dues");
        });
        return model;
    }

    @Override
    public void onStart() {
        super.onStart();
//        item
        binding.add1Item.setOnClickListener(this);
        binding.createItem.setOnClickListener(this);
//        client
        binding.addClient.setOnClickListener(this);
        binding.createClient.setOnClickListener(this);
//        back
        binding.btnBack.setOnClickListener(this);
//        down
        binding.btnDown.setOnClickListener(this);
//        recycler product
        binding.txtVisibleRec.setOnClickListener(this);
        binding.txtDeleteRec.setOnClickListener(this);
        Log.d(TAG, "onStart: " + adapterProduct.totalAll);
    }


    private void loadItems() {
        BottomSheetDialog itemBottomDialog = new BottomSheetDialog(requireContext());
        LayoutItemsBinding itemsBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),
                R.layout.layout_items, getView().findViewById(R.id.container_items), false);
        itemBottomDialog.setContentView(itemsBinding.getRoot());
        itemsBinding.recyclerItemsView.setAdapter(adapter);
        itemsBinding.imageClose.setOnClickListener(v1 -> {
            adapter.stopListening();
            itemBottomDialog.dismiss();
        });
        FrameLayout frameLayout = itemBottomDialog.findViewById(
                com.google.android.material.R.id.design_bottom_sheet);
        if (frameLayout == null) {
            BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        itemBottomDialog.show();
        if (itemBottomDialog.isShowing()) {
            adapter.startListening();
        } else {
            adapter.stopListening();
        }
    }

    private void loadClients() {
        BottomSheetDialog itemBottomDialog = new BottomSheetDialog(requireContext());
        LayoutClientBinding clientBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),
                R.layout.layout_client, getView().findViewById(R.id.container_client), false);
        itemBottomDialog.setContentView(clientBinding.getRoot());
        clientBinding.recyclerItemsView.setAdapter(adapterClient);
        clientBinding.imageClose.setOnClickListener(v1 -> {
            adapterClient.stopListening();
            itemBottomDialog.dismiss();
        });
        FrameLayout frameLayout = itemBottomDialog.findViewById(
                com.google.android.material.R.id.design_bottom_sheet);
        if (frameLayout == null) {
            BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        itemBottomDialog.show();
        if (itemBottomDialog.isShowing()) {
            adapterClient.startListening();
        } else {
            adapter.stopListening();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (binding.createClient.getId() == id) {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentAddReceipt_to_fragmentCreateClient);
        } else if (R.id.create_item == id) {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentAddReceipt_to_fragmentCreateItem);
            Log.d(TAG, "onClick: ");
        } else if (binding.btnBack.getId() == id) {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        } else if (binding.add1Item.getId() == id) {
            loadItems();
        } else if (binding.addClient.getId() == id) {
            loadClients();
        } else if (binding.btnDown.getId() == id) {
            setReceipt();
        } else if (binding.txtVisibleRec.getId() == id) {
            binding.setHasItem(!binding.getHasItem());
        } else if (binding.txtDeleteRec.getId() == id) {
            deleteAllItem();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handleTimeCount.onPause();
    }

    //    TODO edit product
    // product listener
    @Override
    public void editProduct(Products model, int position) {
        DialogConfirm dialogConfirm = new DialogConfirm(requireContext(), new DialogListener() {
            @Override
            public void clickOk(DialogInterface dialog) {

            }

            @Override
            public void clickCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialogConfirm.setResContainer(R.id.products_container);
        FragmentCreateProductsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(dialogConfirm.context()),
                R.layout.fragment_create_products, dialogConfirm.getResContainer(), false);
//        binding.getRoot().setwid
        dialogConfirm.addView(binding.getRoot());
        dialogConfirm.createDialog("Edit product", "asd");
        dialogConfirm.showDialog();
    }

    //    TODO delete product from list
    @Override
    public void deleteProduct(Products model, int position) {
        listProduct.remove(position);
        adapterProduct.notifyItemRemoved(position);
        adapterProduct.notifyItemRangeChanged(position, listProduct.size());
        listItemID.remove(position);
        sizeProduct = listProduct.size();
        binding.setHasItem(sizeProduct > 0);
    }

    @Override
    public void setTotalAll(double total) {
        binding.setTotalAll(String.valueOf(total));
    }

    //    TODO get client object and set name in ui
    // client listener
    @Override
    public void clickClient(Client model, int position) {
        binding.edtClientName.setText(model.getName());
        binding.edtClientPhone.setText(String.valueOf(model.getPhone()));
        setClientId(model.getClientId());

    }

    //    TODO client
    @Override
    public void editClient(Client model) {

    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    //    item listener
//    TODO add item in product
    @Override
    public void clickItem(Products model, Items itemModel, int position) {
        Log.d(TAG, "clickItem: " + itemModel.getItemId());
        listItemID.add(itemModel.getItemId());
        listProduct.add(model);
        adapterProduct.notifyItemInserted(position);
        sizeProduct = listProduct.size();
        binding.setHasItem(true);
    }

    //    TODO edit item
    @Override
    public void editItem(Items model) {

    }

    //    TODO delete all item
    private void deleteAllItem() {
        sizeProduct = 0;
        listProduct.clear();
        listItemID.clear();
        adapterProduct.notifyItemRangeRemoved(0, listProduct.size());
        binding.setHasItem(false);
    }
}