package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentCreateClientBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.view_model.VMCreateClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateClient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateClient extends Fragment {
    private FragmentCreateClientBinding binding;
    private VMCreateClient vmCreateClient;
    private SessionManager manager;
    private NavController controller;
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
        init();
        binding.btnDown.setOnClickListener(v -> postClient());
        binding.btnBack.setOnClickListener(v -> {
            controller.navigate(R.id.action_fragmentCreateClient_to_fragmentAddClient);
//            controller.popBackStack();
        });
    }

    private void init() {
        manager = SessionManager.getInstance(requireContext());
        vmCreateClient = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        )).get(VMCreateClient.class);
        controller = Navigation.findNavController(binding.getRoot());
    }

    private void postClient() {
        Client client = new Client();
        client.setEmail(binding.edtEmail.getText().toString().trim());
        client.setName(binding.edtName.getText().toString().trim());
        client.setPhone(binding.edtClientPhone.getText().toString().trim());
        client.setAddInfo(binding.edtAddInfo.getText().toString().trim());
        client.setTaxRegNo(binding.edtTax4.getText().toString().trim());
        client.setAddress(binding.edtAddress.getText().toString().trim());
        client.setStoreAddress(binding.edtStoreAddress.getText().toString().trim());
        client.setNote(binding.edtNote.getText().toString().trim());
        client.setUserId(manager.getUser().getUserId());

        vmCreateClient.createClient(client).observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()){

                controller.navigate(R.id.action_fragmentCreateClient_to_fragmentAddClient);
            }
            Toast.makeText(requireContext(), cash.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}