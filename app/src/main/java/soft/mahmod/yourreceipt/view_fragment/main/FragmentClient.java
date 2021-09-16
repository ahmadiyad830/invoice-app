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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARClients;
import soft.mahmod.yourreceipt.databinding.FragmentAddClientBinding;
import soft.mahmod.yourreceipt.databinding.FragmentMainClientBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class FragmentClient extends Fragment implements DatabaseUrl, ARClients.OnClickClient, TextWatcher, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FragmentClient";
    private FragmentMainClientBinding binding;
    private ARClients adapter;
    private String[] sortClients = {"name", "email", "phone"};
    private String key = sortClients[0];
    private Query query;
    private FirebaseRecyclerOptions<Client> options;
    private DatabaseReference reference;
    private NavController controller;
    private FragmentClientDirections.ActionMenuClientToFragmentCreateClient2
            argsToCreateClient;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference()
                .child(CLIENT + FirebaseAuth.getInstance().getUid());
        withoutSearch();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable
            ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_client, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        binding.btnAdd.setOnClickListener(v -> {
            controller.navigate(R.id.action_menu_client_to_fragmentCreateClient2);
        });
        spinnerInit();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.recItem.setHasFixedSize(true);
        binding.recItem.setAdapter(adapter);
        adapter.setInMain(true);
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


    private void spinnerInit() {
        binding.spinnerSortList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sortClients);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSortList.setAdapter(adapter);
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

    @Override
    public void clickClient(Client model, int position) {

    }

    @Override
    public void editClient(Client model) {
        argsToCreateClient = FragmentClientDirections.actionMenuClientToFragmentCreateClient2();
        argsToCreateClient.setMainClientToCreateClient(model);
        controller.navigate(argsToCreateClient);
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
}
