package soft.mahmod.yourreceipt.view_fragment.add_receipt.add_products;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARItems;
import soft.mahmod.yourreceipt.databinding.FragmentMainItemsBinding;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddItem extends Fragment implements DatabaseUrl, TextWatcher, ARItems.OnCLickItem, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FragmentAddItem";
    private FragmentMainItemsBinding binding;

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main_items, container, false);
        binding.setInDialog(false);

        return binding.getRoot();
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
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void clickItemToCreateProduct(Products model, Items itemModel, int position) {
        FragmentAddProducts.listProduct.add(model);
    }

    @Override
    public void editItem(Items model) {

    }
}