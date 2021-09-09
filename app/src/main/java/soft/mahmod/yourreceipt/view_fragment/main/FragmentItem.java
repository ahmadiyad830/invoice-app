package soft.mahmod.yourreceipt.view_fragment.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARItems;
import soft.mahmod.yourreceipt.databinding.LayoutItemsBinding;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_fragment.add_receipt.FragmentAddItemDirections;

public class FragmentItem extends Fragment implements ARItems.OnCLickItem, DatabaseUrl, TextWatcher, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FragmentItem";
    private LayoutItemsBinding binding;
    private Query query;
    private ARItems adapter;
    private String[] sortItems = {"itemName", "itemPrice", "quantity"};
    private String key = sortItems[0];
    private FirebaseRecyclerOptions<Items> options;
    private DatabaseReference reference;
    private NavController controller;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference()
                .child(ITEMS + FirebaseAuth.getInstance().getUid());
        withoutSearch();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_items, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        binding.btnAdd.setOnClickListener(v -> {
            controller.navigate(FragmentItemDirections.actionMenuItemToFragmentCreateItem2());
        });
    }

    @Override
    public void clickItemToCreateProduct(Products model, Items itemModel, int position) {

    }

    @Override
    public void editItem(Items model) {

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.recyclerItemsView.setHasFixedSize(true);
        binding.recyclerItemsView.setAdapter(adapter);
        spinnerInit();
        binding.btnDelete.setOnClickListener(v -> {
            binding.textSearch.setText("");
        });

        binding.textSearch.addTextChangedListener(this);
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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
                binding.recyclerItemsView.setAdapter(searchNumber(Double.parseDouble(search)));
            } else {
                binding.recyclerItemsView.setAdapter(search(search));
            }
            binding.setHasValue(true);
        }else {
            binding.recyclerItemsView.setAdapter(withoutSearch());
        }
        binding.setHasValue(!search.isEmpty());
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

    private void spinnerInit() {
        binding.spinnerSortList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sortItems);
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
}
