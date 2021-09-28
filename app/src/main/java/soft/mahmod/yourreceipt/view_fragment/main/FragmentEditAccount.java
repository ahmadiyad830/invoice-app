package soft.mahmod.yourreceipt.view_fragment.main;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.databinding.FragmentEditAccountBinding;
import soft.mahmod.yourreceipt.databinding.LayoutSecurityBinding;
import soft.mahmod.yourreceipt.listeners.ListenerSecurityDialog;
import soft.mahmod.yourreceipt.model.Store;
import soft.mahmod.yourreceipt.statics.DialogSecurity;
import soft.mahmod.yourreceipt.statics.StateCode;
import soft.mahmod.yourreceipt.view_model.database.VMStore;

public class FragmentEditAccount extends Fragment {
    private static final String TAG = "FragmentEditAccount";
    private FragmentEditAccountBinding binding;
    private VMStore vmStore;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmStore = new ViewModelProvider
                (getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMStore.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_account, container, false);
        binding.btnEditStore.setOnClickListener(v -> dialog());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        securityEdit();


    }


    private void securityEdit() {
        VMStore vmStore = new ViewModelProvider
                (getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMStore.class);

        vmStore.getStore().observe(getViewLifecycleOwner(), store -> {

        });
    }

    private void dialogSecurity(String key) {
        ActivityIntent intent = ActivityIntent.getInstance(requireContext());
        DialogSecurity dialogSecurity = new DialogSecurity(requireContext(), getLayoutInflater());
        dialogSecurity.securityDialog(key, new ListenerSecurityDialog() {
            @Override
            public void onOk(Dialog dialog, LayoutSecurityBinding binding) {
                String keySecuirt = binding.edtSecurity.getText().toString().trim();
                if (key != null) {
                    if (key.equals(keySecuirt)) {
                        dialog.dismiss();
                    } else {
                        binding.setError(getResources().getString(R.string.wrong_security_number));
                    }
                } else {
                    intent.userMakeChange(requireActivity());
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancel(Dialog dialog) {
                intent.userMakeChange(requireActivity());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadStore();
    }

    private void dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Edit Account");
        dialog.setMessage("are you sure");
        dialog.setPositiveButton("OK", (dialog1, which) -> {
            createStore();
            dialog1.dismiss();
//            ActivityIntent.getInstance(requireContext()).userMakeChange(requireActivity());
            Toast.makeText(requireContext(), "Your store has been created", Toast.LENGTH_SHORT).show();
        });
        dialog.setNegativeButton("NO", (dialog1, which) -> dialog1.dismiss());
        dialog.create();
        dialog.show();
    }

    private void createStore() {
        vmStore.postStore(getStore()).observe(getViewLifecycleOwner(), cash1 -> {
            if (cash1.getError())
                binding.setError(cash1.getMessage());
        });
    }

    private Store getStore() {
        Store store = new Store();
        String name = binding.edtName.getText().toString().trim();
        store.setName(name);
        String number = binding.edtPhoneNum.getText().toString().trim();
        try {
            store.setPhone(Integer.parseInt(number));
        } catch (NumberFormatException e) {
            store.setPhone(0);
            e.printStackTrace();
        }
        String email = binding.edtEmail.getText().toString().trim();
        store.setEmail(email);
        String address1 = binding.edtAddress.getText().toString().trim();
        store.setAddress(address1);
        return store;
    }

    private void loadStore() {
        vmStore.getStore().observe(getViewLifecycleOwner(), store -> {
            if (!store.getError()) {
                binding.setModel(store);
            }
            Log.d(TAG, "loadStore: " + store.getCode());
        });
    }


}