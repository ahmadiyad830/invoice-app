package soft.mahmod.yourreceipt.view_fragment.main;

import android.os.Bundle;
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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARClients;
import soft.mahmod.yourreceipt.databinding.FragmentAddClientBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class FragmentClient extends Fragment implements DatabaseUrl, ARClients.OnClickClient, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FragmentClient";
    private FragmentAddClientBinding binding;
    private ARClients adapter;
    private String[] sortClients = {"name", "email", "phone"};
    private String key = sortClients[0];

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        FirebaseRecyclerOptions<Client> optionsClient = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(reference.child(CLIENT + FirebaseAuth.getInstance().getUid()), Client.class)
                .build();
        adapter = new ARClients(optionsClient, this);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable
            ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_client, container, false);
        binding.recItem.setHasFixedSize(true);
        spinnerInit();
        binding.recItem.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    public void clickClient(Client model, int position) {

    }

    @Override
    public void editClient(Client model) {

    }

    private void spinnerInit() {
        binding.spinnerSortList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sortClients);
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
