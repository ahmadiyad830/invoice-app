package soft.mahmod.yourreceipt.view_fragment.add_receipt;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARClients;
import soft.mahmod.yourreceipt.common.Common;
import soft.mahmod.yourreceipt.databinding.FragmentAddClientBinding;
import soft.mahmod.yourreceipt.databinding.FragmentAddProductsBinding;
import soft.mahmod.yourreceipt.databinding.FragmentCreateClientBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_fragment.create.MainCreateClient;
import soft.mahmod.yourreceipt.view_model.database.VMClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddClient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddClient extends Fragment implements ARClients.OnClickClient, DatabaseUrl, TextWatcher, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FragmentAddClient";
    private FragmentAddClientBinding binding;
    private NavController controller;
    private ARClients adapter;
    private String[] sortClients = {"name", "email", "phone"};
    private String key = sortClients[0];
    private Query query;
    private FirebaseRecyclerOptions<Client> options;
    private DatabaseReference reference;
    private VMClient vmClient;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmClient = new ViewModelProvider(getViewModelStore(),new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMClient.class);
        reference = FirebaseDatabase.getInstance().getReference()
                .child(CLIENT + FirebaseAuth.getInstance().getUid());
        withoutSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_client, container, false);
        binding.setIsCreateReceipt(true);
        init();
        return binding.getRoot();
    }
    private void init() {
        binding.recItem.setHasFixedSize(true);
        binding.recItem.setAdapter(adapter);
        spinnerInit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         controller = Navigation.findNavController(view);

         binding.btnAdd.setOnClickListener(v -> {
             dialogCreateClient();
         });
    }

    private void dialogCreateClient() {
        Dialog dialog = new Dialog(requireContext());
        FragmentCreateClientBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_create_client
                , null, false);
        dialog.setContentView(binding.getRoot());
//        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.custom_back_icon));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        binding.btnDown.setOnClickListener(v -> {

            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.recItem.setHasFixedSize(true);
        binding.recItem.setAdapter(adapter);
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
    public void onDestroy() {
        super.onDestroy();
        binding = null;

        getViewModelStore().clear();
    }

    @Override
    public void clickClient(Client model, int position) {
        FragmentAddClientDirections.ActionAddClientToMainAddItem2 argsClient
                = FragmentAddClientDirections.actionAddClientToMainAddItem2();
        argsClient.setClientToAddItem(model);
        controller.navigate(argsClient);
    }

    @Override
    public void editClient(Client model) {

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
            if (!key.equals(sortClients[0])) {
                binding.recItem.setAdapter(searchNumber(Double.parseDouble(search)));
            } else {
                binding.recItem.setAdapter(search(search));
            }
            binding.setHasValue(true);
        } else {
            binding.recItem.setAdapter(withoutSearch());
        }
        binding.setHasValue(!search.isEmpty());
    }
    private void spinnerInit() {
        binding.spinnerSortList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_style, sortClients);
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
    private ARClients searchNumber(double search) {
        query = reference;
        options = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(query.orderByChild(key).startAt(search).endAt(search + "\uf8ff"), Client.class)
                .build();
        adapter = new ARClients(options, this);
        adapter.startListening();
        return adapter;
    }

    private ARClients search(String search) {
        query = reference;
        options = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(query.orderByChild(key).startAt(search).endAt(search + "\uf8ff"), Client.class)
                .build();
        adapter = new ARClients(options, this);
        adapter.startListening();
        return adapter;
    }

    private ARClients withoutSearch() {
        options = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(reference, Client.class)
                .build();
        adapter = new ARClients(options, this);
        adapter.startListening();
        return adapter;
    }
}