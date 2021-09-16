package soft.mahmod.yourreceipt.view_fragment.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateClientBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.view_model.database.VMClient;

public class MainCreateClient extends Fragment {
    private static final String TAG = "FragmentCreateClient";
    private FragmentCreateClientBinding binding;
    private VMClient vmClient;
    private NavController controller;
    private final List<String> listWarning = new ArrayList<>();
    private boolean isEdit = false;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmClient = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(
                        requireActivity().getApplication()
                )
        ).get(VMClient.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_client, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainCreateClientArgs addClientArgs = MainCreateClientArgs.fromBundle(getArguments());
        if (addClientArgs.getMainClientToCreateClient() != null){
            isEdit = true;
            binding.setModel(addClientArgs.getMainClientToCreateClient());
        }
        binding.btnDown.setOnClickListener(v -> {
            if (isEdit) {
                putClient(addClientArgs.getMainClientToCreateClient().getClientId());
            } else {
                postClient();
            }
            requireActivity().onBackPressed();
        });
    }


    private void postClient() {
        vmClient.postClient(getClient()).observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()) {
                requireActivity().onBackPressed();
            } else Toast.makeText(requireContext(), cash.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void putClient(String id) {
        Client client = getClient();
        client.setClientId(id);
        vmClient.putClient(getClient()).observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()) {
                requireActivity().onBackPressed();
            } else Toast.makeText(requireContext(), cash.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private Client getClient() {
        Client client = new Client();
        try {
            client.setClientId(binding.getModel().getClientId());
        } catch (Exception ignored) {
        }
        client.setEmail(binding.edtEmail.getText().toString().trim());
        client.setName(binding.edtName.getText().toString().trim());
        try {
            client.setPhone(Integer.parseInt(binding.edtClientPhone.getText().toString().trim()));
        } catch (NumberFormatException e) {
            client.setPhone(0);
        }
        client.setAddInfo(binding.edtAddInfo.getText().toString().trim());
        client.setTaxRegNo(binding.switchTax.isChecked());
        client.setAddress(binding.edtAddress.getText().toString().trim());
        client.setStoreAddress(binding.edtStoreAddress.getText().toString().trim());
        client.setNote(binding.edtNote.getText().toString().trim());
        return client;
    }
}
