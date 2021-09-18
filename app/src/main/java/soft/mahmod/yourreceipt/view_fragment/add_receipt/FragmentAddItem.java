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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
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

    private final List<String> listWarning = new ArrayList<>();
    private int sizeProduct = 0;
    public final static List<Products> listProduct = new ArrayList<>();
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
    public void clickItem(Products model, Items itemModel, int position) {
        FragmentAddItemDirections.ActionFragmentAddItemToFragmentCreateItem2
                createItem = FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateItem2();
        createItem.setItemToCreateItem(itemModel);
        controller.navigate(createItem);
        // FIXME: 9/10/2021
//        itemBottomDialog.dismiss();
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
    //    bottom sheet
    @Override
    public void onStart() {
        super.onStart();
        loadItems(requireView());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        spinnerInit(spinnerSort);
        btnCleanText.setOnClickListener(v -> {
            searchEdit.setText("");
        });

        searchEdit.addTextChangedListener(this);
        adapter.startListening();
    }
    private EditText searchEdit;
    private ImageView btnCleanText;
    private Spinner spinnerSort;
    private MaterialButton btnNewItem;
    private RecyclerView recyclerView;
    private BottomAppBar bottomAppBar;
    private ConstraintLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView stateBottomSheet;
    private void loadItems(View view) {
        btnCleanText = view.findViewById(R.id.btn_clean);
        searchEdit = view.findViewById(R.id.text_search);
        spinnerSort = view.findViewById(R.id.spinner_sort_list);
        btnNewItem = view.findViewById(R.id.btn_add);
        btnNewItem = view.findViewById(R.id.btn_add);
        recyclerView = view.findViewById(R.id.recycler_items_view);
        bottomSheet = view.findViewById(R.id.container_items);
        stateBottomSheet = view.findViewById(R.id.btn_slide);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(withoutSearch());
        stateBottomSheet.setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        searchEdit.addTextChangedListener(this);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                stateBottomSheet.setRotation(slideOffset * 180);
            }
        });

        spinnerInit(spinnerSort);
        btnNewItem.setOnClickListener(v -> {
            controller.navigate(FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateItem2());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
        adapter.startListening();
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
        recyclerView.setHasFixedSize(true);
        if (!search.isEmpty()){
            if (!key.equals(sortItems[0])) {
                recyclerView.setAdapter(searchNumber(Double.parseDouble(search)));
            } else {
                recyclerView.setAdapter(search(search));

            }
//            itemsBinding.setHasValue(true);
        }else {
            recyclerView.setAdapter(withoutSearch());
        }
//        itemsBinding.setHasValue(!search.isEmpty());
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