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

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateClientBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.view_model.database.VMClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateClient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateClient extends Fragment {
    private static final String TAG = "FragmentCreateClient";
    private FragmentCreateClientBinding binding;
    private VMClient vmClient;
    private NavController controller;

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
        controller = Navigation.findNavController(view);
        binding.btnDown.setOnClickListener(v -> {
            postClient();
        });
        binding.btnBack.setOnClickListener(v -> {

//            controller.navigate(FragmentCreateClientDirections.actionFragmentCreateClientToFragmentAddReceipt());
//            controller.popBackStack();
        });
    }

    private void postClient() {
        vmClient.postClient(getClient()).observe(getViewLifecycleOwner(),cash -> {
            if (!cash.getError()){
//                controller.navigate(FragmentCreateClientDirections.actionFragmentCreateClientToFragmentAddReceipt());
            }
            Log.d(TAG, "postClient: " + cash.toString());
        });
    }

    private Client getClient() {
        Client client = new Client();
        client.setEmail(binding.edtEmail.getText().toString().trim());
        client.setName(binding.edtName.getText().toString().trim());
        try {
            // FIXME: 9/1/2021
            client.setPhone(Integer.parseInt(binding.edtClientPhone.getText().toString().trim()));
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        client.setAddInfo(binding.edtAddInfo.getText().toString().trim());
        try {
            client.setTaxRegNo(Double.parseDouble(binding.edtTax4.getText().toString().trim()));
        } catch (NumberFormatException e) {
            // FIXME: 9/1/2021
            e.printStackTrace();
        }
        client.setAddress(binding.edtAddress.getText().toString().trim());
        client.setStoreAddress(binding.edtStoreAddress.getText().toString().trim());
        client.setNote(binding.edtNote.getText().toString().trim());
        return client;
    }
}