package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.content.DialogInterface;
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
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateClientBinding;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
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
    private final List<String> listWarning = new ArrayList<>();
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
        FragmentCreateClientArgs addClientArgs = FragmentCreateClientArgs.fromBundle(getArguments());
        if (addClientArgs != null)
            binding.setModel(addClientArgs.getEditClient());
        controller = Navigation.findNavController(view);
        binding.fabToCreateClient.setOnClickListener(v -> {
            if (warningClient()) {
                dialogWarning();
            } else postClient();

        });

    }


    private Client getClient() {
        Client client = new Client();
        client.setEmail(binding.edtEmail.getText().toString().trim());
        client.setName(binding.edtName.getText().toString().trim());
        try {
            client.setPhone(Integer.parseInt(binding.edtClientPhone.getText().toString().trim()));
        } catch (NumberFormatException e) {
            client.setPhone(0);
        }
        client.setAddInfo(binding.edtAddInfo.getText().toString().trim());
        try {
            client.setTaxRegNo(Double.parseDouble(binding.edtTax4.getText().toString().trim()));
        } catch (NumberFormatException e) {
            client.setTaxRegNo(0.0);
        }
        client.setAddress(binding.edtAddress.getText().toString().trim());
        client.setStoreAddress(binding.edtStoreAddress.getText().toString().trim());
        client.setNote(binding.edtNote.getText().toString().trim());
        return client;
    }

    private void postClient() {
        vmClient.postClient(getClient()).observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()) {
                requireActivity().onBackPressed();
                controller.navigate(FragmentCreateClientDirections.actionFragmentCreateClientToFragmentAddClient());
            } else Toast.makeText(requireContext(), cash.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private boolean warningClient() {
        if (getClient().getName().equals("")) {
            listWarning.add(getResources().getString(R.string.client_name));
        }
        if (getClient().getPhone() == 0) {
            listWarning.add(getResources().getString(R.string.client_phone));
        }
        if (getClient().getStoreAddress().equals("")) {
            listWarning.add(getResources().getString(R.string.store_address));
        }
        return listWarning.size() > 0;
    }
    private void dialogWarning() {
        DialogConfirm dialogConfirm = new DialogConfirm(requireContext());
        dialogConfirm.setDialogListener(new DialogListener() {
            @Override
            public void clickOk(DialogInterface dialog) {
                listWarning.clear();
                postClient();
            }

            @Override
            public void clickCancel(DialogInterface dialog) {
                dialog.dismiss();
                listWarning.clear();
            }
        });
        dialogConfirm.addIcon(R.drawable.ic_twotone_warning_24);
        dialogConfirm.listenerDialog();
//        TODO translate
        StringBuilder warning = new StringBuilder("\n");
        for (int i = 0; i < listWarning.size(); i++) {
            warning.append(listWarning.get(i)).append("\n");
        }
        dialogConfirm.createDialog(getResources().getString(R.string.warning),
                getResources().getString(R.string.warning_not_add)
                        + warning.toString()
        );
        dialogConfirm.showDialog();
    }
}