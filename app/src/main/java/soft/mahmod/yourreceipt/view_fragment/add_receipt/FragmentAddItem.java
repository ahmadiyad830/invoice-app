package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
import soft.mahmod.yourreceipt.databinding.LayoutItemsBinding;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddItem extends Fragment implements ARItems.OnCLickItem, ARProducts.OnClickItem, DatabaseUrl {
    private static final String TAG = "FragmentAddItem";
    private FragmentAddItemBinding binding;
    private ARItems arItems;
    private List<Items> modelList = new ArrayList<>();
    private NavController controller;
    private HandleTimeCount handleTimeCount;
    private final List<Products> listProduct = new ArrayList<>();
    private int sizeProduct = 0, increment = 0;
    private ARProducts arProducts;
    private DatabaseReference reference;
    private Query query;
    private FirebaseRecyclerOptions<Items> options;

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
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);

        binding.fabToCreateItem.setOnClickListener(v -> {
            loadItems();
        });
        binding.txtDeleteAll.setOnClickListener(v -> {
            sizeProduct = listProduct.size();
            if (sizeProduct>0){
                arProducts.notifyItemRangeRemoved(0,sizeProduct);
                listProduct.clear();
                sizeProduct = 0;
                setTotalAll(0.0);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        getViewModelStore().clear();
    }


    // TODO listener items
    @Override
    public void clickItem(Products model, Items itemModel, int position) {
        int oldSize = listProduct.size();
        listProduct.add(model);
        arProducts.notifyItemRangeInserted(oldSize, listProduct.size());
    }

    @Override
    public void editItem(Items model) {
        FragmentAddItemDirections.ActionFragmentAddItemToFragmentCreateItem5
                createItem = FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateItem5();
        createItem.setEditItem(model);
        controller.navigate(createItem);
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

    private void loadItems() {
        BottomSheetDialog itemBottomDialog = new BottomSheetDialog(requireContext());
        LayoutItemsBinding itemsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(requireContext())
                , R.layout.layout_items
                , requireView().findViewById(R.id.container_items)
                , false);
        itemsBinding.setIsSearch(false);
        itemBottomDialog.setContentView(itemsBinding.getRoot());
        itemsBinding.recyclerItemsView.setAdapter(arItems);
        itemsBinding.btnSearch.setOnClickListener(v1 -> {
            itemsBinding.setIsSearch(true);
            String search = itemsBinding.textSearch.getText().toString().trim();
            itemsBinding.textSearch.setFocusable(true);
            if (!search.isEmpty()) {
                setQuery(reference.child(RECEIPT + FirebaseAuth.getInstance().getUid())
                        .orderByChild("itemName").startAt(search).endAt(search + "\uf8ff"));
            }
        });
        itemsBinding.btnAdd.setOnClickListener(v -> {
            controller.navigate(FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateItem5());
        });
        itemBottomDialog.show();
        if (itemBottomDialog.isShowing()) {
            arItems.startListening();
        } else {
            arItems.stopListening();
        }
    }
}