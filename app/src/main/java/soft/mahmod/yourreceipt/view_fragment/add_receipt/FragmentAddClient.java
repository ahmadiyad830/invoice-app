package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARClients;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentAddClientBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddClient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddClient extends Fragment implements ARClients.OnClickClient, DatabaseUrl {
    private static final String TAG = "FragmentAddClient";
    private FragmentAddClientBinding binding;
    private ARClients adapterClient;
    private NavController controller;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        FirebaseRecyclerOptions<Client> optionsClient = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(reference.child(CLIENT + FirebaseAuth.getInstance().getUid()), Client.class)
                .build();
        adapterClient = new ARClients(optionsClient, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_client, container, false);
        init();
        return binding.getRoot();
    }
    private void init() {
        binding.recItem.setHasFixedSize(true);
        binding.recItem.setAdapter(adapterClient);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         controller = Navigation.findNavController(view);
        binding.fabToCreateClient.setOnClickListener(v -> {

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterClient.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        adapterClient.stopListening();
        getViewModelStore().clear();
    }

    @Override
    public void clickClient(Client model, int position) {
        FragmentAddClientDirections.ActionFragmentAddClientToFragmentAddItem3 argsClient
                = FragmentAddClientDirections.actionFragmentAddClientToFragmentAddItem3();
        argsClient.setClientToItem(model);
        FragmentAddClientArgs argsReceipt = FragmentAddClientArgs.fromBundle(getArguments());
        argsReceipt.getReceiptToClient().setClientName(model.getName());
        argsReceipt.getReceiptToClient().setClientPhone(model.getPhone());
        argsReceipt.getReceiptToClient().setClientId(model.getClientId());
        argsClient.setReceiptToItems(argsReceipt.getReceiptToClient());
        controller.navigate(argsClient);
        Log.d(TAG, "clickClient: "+argsReceipt.getReceiptToClient().toString());
    }

    @Override
    public void editClient(Client model) {
        FragmentAddClientDirections.ActionFragmentAddClientToFragmentCreateClient3 editClient
                = FragmentAddClientDirections.actionFragmentAddClientToFragmentCreateClient3();
        editClient.setEditClient(model);
        controller.navigate(editClient);
    }
}