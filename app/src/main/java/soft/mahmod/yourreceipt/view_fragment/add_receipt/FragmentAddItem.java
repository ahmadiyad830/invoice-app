package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import soft.mahmod.yourreceipt.databinding.FragmentItemsBinding;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;
import soft.mahmod.yourreceipt.view_model.storage.VMInvoice;

public class FragmentAddItem extends Fragment implements ARItems.OnCLickItem, ARProducts.OnClickItem, DatabaseUrl, TextWatcher {
    private static final String TAG = "FragmentAddItem";
    private FragmentAddItemBinding binding;
    private ARItems arItems;
    private NavController controller;
    private HandleTimeCount handleTimeCount;
    public final static List<Products> listProduct = new ArrayList<>();
    private final List<String> listWarning = new ArrayList<>();
    private int sizeProduct = 0;
    private ARProducts arProducts;
    private DatabaseReference reference;
    private Query query;
    private VMReceipt vmReceipt;
    private VMInvoice vmInvoice;
    private FragmentAddItemArgs addItemArgs;
    private FragmentAddItemDirections.ActionFragmentAddItemToFragmentCreateProducts4 addProduct;
    private String date;
    public void setQuery(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (reference == null) {
            reference = FirebaseDatabase.getInstance().getReference();
            setQuery(reference.child(ITEMS + FirebaseAuth.getInstance().getUid()));
        }
        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(getQuery(), Items.class)
                .build();
        arItems = new ARItems(options, this);
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
            controller.navigate(R.id.action_fragmentAddItem_to_fragmentPrintReceipt);
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        addItemArgs = FragmentAddItemArgs.fromBundle(getArguments());
        addProduct = FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateProducts4();

        binding.txtMyItems.setOnClickListener(v -> {
            loadItems();
        });
        binding.fabToCreateItem.setOnClickListener(v -> {
            setReceipt();
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

    private void setReceipt() {
        DialogConfirm confirm = new DialogConfirm(requireContext());
        confirm.setIcon(R.mipmap.ic_launcher)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancel(true);
        FragmentAddItemDirections.ActionFragmentAddItemToFragmentPrintReceipt printReceipt
                = FragmentAddItemDirections.actionFragmentAddItemToFragmentPrintReceipt();
        printReceipt.setArgsReceiptPrint(getReceipt());
        controller.navigate(printReceipt);
//        vmReceipt.postReceipt(getReceipt()).observe(getViewLifecycleOwner(), cashReceipt -> {
//
//        });
    }

    private Receipt getReceipt() {
        Receipt model = addItemArgs.getReceiptToItems();
        model.setDate(handleTimeCount.getDate());
        Log.d(TAG, "getReceipt: "+handleTimeCount.getDate());
        model.setProducts(listProduct);
        model.setClientId(addItemArgs.getClientToItem().getClientId());
        model.setClientPhone(addItemArgs.getClientToItem().getPhone());
        model.setClientName(addItemArgs.getClientToItem().getName());
        return model;
    }


    // TODO listener items
    @Override
    public void clickItemToCreateProduct(Products model, Items itemModel, int position) {
        model.setTaxClientNoReg(addItemArgs.getClientToItem().isTaxRegNo());
        addProduct.setArgsProduct(model);
        controller.navigate(addProduct);
        itemBottomDialog.dismiss();
    }

    @Override
    public void editItem(Items model) {
        FragmentAddItemDirections.ActionFragmentAddItemToFragmentCreateItem5
                createItem = FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateItem5();
        createItem.setEditItem(model);
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
    private void loadItems() {
        itemBottomDialog = new BottomSheetDialog(requireContext());
        FragmentItemsBinding itemsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(requireContext())
                , R.layout.fragment_items
                , requireView().findViewById(R.id.container_items)
                , false);
        itemBottomDialog.setContentView(itemsBinding.getRoot());
        itemsBinding.recyclerItemsView.setAdapter(arItems);
        itemsBinding.textSearch.addTextChangedListener(this);

        itemsBinding.btnAdd.setOnClickListener(v -> {
            controller.navigate(FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateItem5());
            itemBottomDialog.dismiss();
        });
        itemBottomDialog.show();
        if (itemBottomDialog.isShowing()) {
            arItems.startListening();
        } else {
            arItems.stopListening();
        }
    }

    private void dialogWarning() {
        DialogConfirm dialogConfirm = new DialogConfirm(requireContext());
        dialogConfirm.setDialogListener(new DialogListener() {
            @Override
            public void clickOk(DialogInterface dialog) {
                listWarning.clear();
                getReceipt();
            }

            @Override
            public void clickCancel(DialogInterface dialog) {
                dialog.dismiss();
                listWarning.clear();
            }
        });
        dialogConfirm.setIcon(R.drawable.ic_twotone_warning_24);
        dialogConfirm.listenerDialog();
//        TODO translate
        StringBuilder warning = new StringBuilder("\n");
        for (int i = 0; i < listWarning.size(); i++) {
            warning.append(listWarning.get(i)).append("\n");
        }
        dialogConfirm.createDialog(getResources().getString(R.string.warning),
                getResources().getString(R.string.warning_not_add)
                        + warning.toString()
        );
        dialogConfirm.showDialog();
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
        if (!search.isEmpty()) {
            setQuery(reference.child(RECEIPT + FirebaseAuth.getInstance().getUid())
                    .orderByChild("itemName").startAt(search).endAt(search + "\uf8ff"));
        }
    }
}