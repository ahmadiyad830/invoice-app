package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.adapter.firebase.ARItems;
import soft.mahmod.yourreceipt.databinding.FragmentAddItemBinding;
import soft.mahmod.yourreceipt.databinding.FragmentMainItemsBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;
import soft.mahmod.yourreceipt.view_model.storage.VMInvoice;

public class FragmentAddItem extends Fragment implements ARItems.OnCLickItem, ARProducts.OnClickItem, DatabaseUrl, TextWatcher, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FragmentAddItem";
    private FragmentAddItemBinding binding;
    private NavController controller;
    private HandleTimeCount handleTimeCount;
    public final static List<Products> listProduct = new ArrayList<>();
    private final List<String> listWarning = new ArrayList<>();
    private int sizeProduct = 0;
    private ARProducts arProducts;
    private VMReceipt vmReceipt;
    private VMInvoice vmInvoice;
    private FragmentAddItemArgs addItemArgs;
    private FragmentAddItemDirections.ActionAddItemToCreateProducts4 addProduct;
    private String date;

    private Query query;
    private ARItems adapter;
    private String[] sortItems = {"itemName", "itemPrice", "quantity"};
    private String key = sortItems[0];
    private FirebaseRecyclerOptions<Items> options;
    private DatabaseReference reference;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference()
                .child(ITEMS + FirebaseAuth.getInstance().getUid());
        withoutSearch();
        vmReceipt = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMReceipt.class);
        vmInvoice = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMInvoice.class);
        handleTimeCount = new HandleTimeCount();
        handleTimeCount.countDownStart();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false);


        handleTimeCount = new HandleTimeCount();
        arProducts = new ARProducts(listProduct, this);
        init();
        return binding.getRoot();
    }
    private void init() {
        binding.recItem.setHasFixedSize(true);
        binding.recItem.setAdapter(arProducts);
        binding.total.setOnClickListener(v -> {
            controller.navigate(R.id.action_AddItem_to_AddReceipt);
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        addItemArgs = FragmentAddItemArgs.fromBundle(getArguments());
        addProduct = FragmentAddItemDirections.actionAddItemToCreateProducts4();

        binding.txtMyItems.setOnClickListener(v -> {
            loadItems();
        });
        binding.fabToCreateItem.setOnClickListener(v -> {
            passReceipt();
        });
        binding.txtDeleteAll.setOnClickListener(v -> {
            sizeProduct = listProduct.size();
            if (sizeProduct > 0) {
                arProducts.notifyItemRangeRemoved(0, sizeProduct);
                listProduct.clear();
                sizeProduct = 0;
                setTotalAll(0.0);
            }
        });
    }

    private void passReceipt() {
        DialogConfirm confirm = new DialogConfirm(requireContext());
        confirm.setIcon(R.mipmap.ic_launcher)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancel(true);
        FragmentAddItemDirections.ActionAddItemToAddReceipt toReceipt
                = FragmentAddItemDirections.actionAddItemToAddReceipt();
        toReceipt.setReceiptToAddReceipt(getReceipt());
        controller.navigate(toReceipt);
    }

    private Receipt getReceipt() {
        Client client = addItemArgs.getClientToAddItem();
        Receipt model = new Receipt();
        model.setClientName(client.getName());
        model.setClientId(client.getClientId());
        model.setClientPhone(model.getClientPhone());
        model.setDate(handleTimeCount.getDate());
        model.setProducts(listProduct);
        model.setClientId(client.getClientId());
        model.setClientPhone(client.getPhone());
        model.setClientName(client.getName());
        return model;
    }


    // TODO listener items
    @Override
    public void clickItemToCreateProduct(Products model, Items itemModel, int position) {
        model.setTaxClientNoReg(addItemArgs.getClientToAddItem().isTaxRegNo());
        addProduct.setItemToCreateProduct(model);
        controller.navigate(addProduct);
        itemBottomDialog.dismiss();
    }

    @Override
    public void editItem(Items model) {
        FragmentAddItemDirections.ActionFragmentAddItemToFragmentCreateItem2
                createItem = FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateItem2();
        createItem.setItemToCreateItem(model);
        controller.navigate(createItem);
        itemBottomDialog.dismiss();
    }

    // TODO listener products
    @Override
    public void editProduct(Products model, int position) {

    }

    @Override
    public void deleteProduct(Products model, int position) {
        binding.setTotalAll(binding.getTotalAll() - listProduct.get(position).getTotal());
        listProduct.remove(position);
        arProducts.notifyItemRemoved(position);
    }

    @Override
    public void setTotalAll(double total) {
        binding.setTotalAll(total);
    }

    private BottomSheetDialog itemBottomDialog;
    FragmentMainItemsBinding itemsBinding;
    private void loadItems() {
        itemBottomDialog = new BottomSheetDialog(requireContext());
         itemsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(requireContext())
                , R.layout.fragment_main_items
                , requireView().findViewById(R.id.container_items)
                , false);
        itemBottomDialog.setContentView(itemsBinding.getRoot());
        itemsBinding.recyclerItemsView.setAdapter(adapter);
        if (itemBottomDialog.isShowing()){
            itemsBinding.textSearch.addTextChangedListener(this);
            spinnerInit(itemsBinding.spinnerSortList);
        }
        itemsBinding.btnAdd.setOnClickListener(v -> {
            controller.navigate(FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateItem2());
            itemBottomDialog.dismiss();
        });
        itemBottomDialog.show();
        if (itemBottomDialog.isShowing()) {
            adapter.startListening();
        } else {
            adapter.stopListening();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        getViewModelStore().clear();
    }

    private boolean warning() {
        if (listProduct.size() < 1) {
            listWarning.add(getResources().getString(R.string.products));
        }
        return listWarning.size() > 0;
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
                itemsBinding.recyclerItemsView.setAdapter(searchNumber(Double.parseDouble(search)));
            } else {
                itemsBinding.recyclerItemsView.setAdapter(search(search));
            }
            itemsBinding.setHasValue(true);
        }else {
            itemsBinding.recyclerItemsView.setAdapter(withoutSearch());
        }
        itemsBinding.setHasValue(!search.isEmpty());
    }
    private void spinnerInit(Spinner spinner) {
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sortItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        key = (String) parent.getItemAtPosition(position);
        Log.d(TAG, "onItemSelected: " + key);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        key = (String) parent.getItemAtPosition(0);
    }
}