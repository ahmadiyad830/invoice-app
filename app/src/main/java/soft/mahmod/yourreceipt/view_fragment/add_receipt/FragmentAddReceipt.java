package soft.mahmod.yourreceipt.view_fragment.add_receipt;

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
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.databinding.LayoutClientBinding;
import soft.mahmod.yourreceipt.databinding.LayoutItemsBinding;
import soft.mahmod.yourreceipt.listeners.OnClickItemListener;
import soft.mahmod.yourreceipt.listeners.OnClientClick;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.CreateReceipt;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_activity.MainActivity;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddReceipt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddReceipt extends Fragment implements View.OnClickListener
        , ARItems.OnCLickItem, ARClients.OnClickClient, DatabaseUrl, ARProducts.OnClickItem {
    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    private HandleTimeCount handleTimeCount;
    private VMReceipt vmReceipt;
    private ARItems adapter;
    private ARClients adapterClient;
    private final List<Long> listItemID = new ArrayList<>();
    private final List<Products> listProduct = new ArrayList<>();
    private ARProducts adapterProduct;
    private int sizeProduct = 0;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        vmReceipt = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMReceipt.class);
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
        binding.setHasItem(true);
        CreateReceipt model = new CreateReceipt();

        handleTimeCount.setTv_time(binding.txtTime);
        handleTimeCount.countDownStart();
        binding.setDate(handleTimeCount.getDate());

        binding.switchPrint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.setChecked(isChecked);
        });


        binding.setModel(model);
        return binding.getRoot();
    }

    private void init() {
        binding.itemsRec.setHasFixedSize(true);
        binding.itemsRec.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.itemsRec.setAdapter(adapterProduct);
    }

    private void setReceipt() {
        vmReceipt.setReceipt(getReceipt(listItemID));
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

    private Receipt getReceipt(List<Long> listItemId) {
        Receipt model = new Receipt();
        model.setSubject(binding.edtSubject.getText().toString().trim());
        model.setReceiptDate("time: " + binding.txtTime.getText().toString().trim() + " date: " + handleTimeCount.getDate());
        model.setTotalAll(binding.edtTotalAll.getText().toString().trim());
//        model.setClientName();
        model.setClientPhone(binding.edtClientPhone.getText().toString().trim());
        model.setItemId(listItemId);
        // FIXME: 8/30/2021 client id not name
//        model.setClientId(binding.edtClientName.getText().toString().trim());
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
        } else if (binding.refreshProducts.getId() == id) {
            binding.setHasItem(false);
            binding.setHasItem(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handleTimeCount.onPause();
    }

    //    TODO update product like price or quantity or discount and tax
    // product listener
    @Override
    public void clickProduct(Products model, int position) {

    }

    //    TODO delete product from list
    @Override
    public void deleteProduct(Products model, int position) {
        listProduct.remove(position);
        adapterProduct.notifyItemRemoved(position);
        adapterProduct.notifyItemRangeChanged(position, listProduct.size());
        sizeProduct = listProduct.size();
        binding.setHasItem(sizeProduct > 0);
    }

    //    TODO get client object and set name in ui
    // client listener
    @Override
    public void clickClient(Client model, int position) {
        binding.edtClientName.setText(model.getName());
    }

    //    item listener
//    TODO add item in product
    @Override
    public void clickItem(Products model, int position) {
        listItemID.add(model.getProductId());
        listProduct.add(model);
        adapterProduct.notifyItemInserted(position);
        sizeProduct = listProduct.size();
        binding.setHasItem(true);
    }

    //    TODO total all product price
    private String totalAll() {
        double total = 0.0;
        for (Products model : listProduct) {
            total = model.getProductsPrice() * model.getProductsQuantity();
        }
        return String.valueOf(total);
    }

    //    TODO delete all item
    private void deleteAllItem() {
        sizeProduct = 0;
        listProduct.clear();
        adapterProduct.notifyItemRangeRemoved(0, listProduct.size());
        binding.setHasItem(false);
    }
}