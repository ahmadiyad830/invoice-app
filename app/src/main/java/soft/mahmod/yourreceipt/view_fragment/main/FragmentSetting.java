package soft.mahmod.yourreceipt.view_fragment.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SecurityManager;
import soft.mahmod.yourreceipt.databinding.FragmentSettingBinding;
import soft.mahmod.yourreceipt.dialog.DialogSecurity;
import soft.mahmod.yourreceipt.listeners.ListenerSecurityDialog;
import soft.mahmod.yourreceipt.model.Store;
import soft.mahmod.yourreceipt.view_model.auth.VMSettingAuth;
import soft.mahmod.yourreceipt.view_model.condition.VMSignConditions;
import soft.mahmod.yourreceipt.view_model.database.VMStore;
import soft.mahmod.yourreceipt.view_model.database.VMUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentSetting";
    private FragmentSettingBinding binding;
    private SecurityManager manager;
    private VMStore vmStore;
    private VMUser vmUser;
    private VMSettingAuth vmSettingAuth;
    private VMSignConditions vmSignConditions;
    private boolean editStore = true;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmStore = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMStore.class);
        vmUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMUser.class);
        vmSettingAuth = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMSettingAuth.class);
        vmSignConditions = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMSignConditions.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        manager = SecurityManager.getInectance(requireContext());

        loadStore();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.txtVisiblePassword.setOnClickListener(this);
        binding.txtVisibleStore.setOnClickListener(this);
        binding.txtVisibleSeurity.setOnClickListener(this);
        binding.txtForgetPassword.setOnClickListener(this);
        binding.btnChangePassword.setOnClickListener(this);
        binding.btnEditStore.setOnClickListener(this);
        binding.btnSecurity.setOnClickListener(this);
        binding.txtLocationVisibleSeurity.setOnClickListener(this);

        binding.boxDontShow.setChecked(manager.isShow());
        binding.boxDontShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            manager.setShow(isChecked);
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.txtVisibleStore.getId()) {
            binding.setVisibleStore(true);
            binding.setVisiblePassword(!binding.getVisibleStore());
            binding.setVisibleSecurity(!binding.getVisibleStore());
        } else if (id == binding.txtVisiblePassword.getId()) {
            binding.setVisiblePassword(true);
            binding.setVisibleStore(!binding.getVisiblePassword());
            binding.setVisibleSecurity(!binding.getVisiblePassword());
        } else if (id == binding.txtVisibleSeurity.getId()) {
            binding.setVisibleSecurity(true);
            binding.setVisiblePassword(!binding.getVisibleSecurity());
            binding.setVisibleStore(!binding.getVisibleSecurity());
        } else if (id == binding.btnEditStore.getId()) {
            postStore();
        } else if (id == binding.btnChangePassword.getId()) {
            postNewPassword();
        } else if (id == binding.btnSecurity.getId()) {
            postSecurityNumber();
        } else if (id == binding.txtForgetPassword.getId()) {
            Log.d(TAG, "txtForgetPassword: ");
            dialogForgetPassword().show();
        }else if (id == binding.txtLocationVisibleSeurity.getId()){
            dialogSecurity();
        }
    }

    private void dialogSecurity() {
        DialogSecurity dialogSecurity = new DialogSecurity(requireContext(),getLayoutInflater());
        dialogSecurity.inSetting = true;
        if (!dialogSecurity.hasKey()){
            binding.setVisibleLocationSecurity(true);
            binding.setErrorLocationKeySecurity("not have key");
            return;
        }
        dialogSecurity.securityDialog(new ListenerSecurityDialog() {
            @Override
            public void onOk(Dialog dialog, boolean notWrong) {
//                manager.setShow(!notWrong);
//                binding.boxDontShow
                dialog.dismiss();
            }

            @Override
            public void onCancel(Dialog dialog) {
                dialog.dismiss();
            }
        });
    }

    private void postSecurityNumber() {
        binding.btnSecurity.setEnabled(false);
        String password = binding.passwordEditSecurity.getText().toString();
        String keySecurity = binding.edtSecurity.getText().toString();
        if (manager.password().equals(password)) {
            if (keySecurity.length() == 4) {
                vmUser.putSecurityKey(keySecurity).observe(getViewLifecycleOwner(), user -> {
                    if (!user.getError()) {
                        manager.setKeySecuirty(keySecurity);
                        if (user.getCode() == 200){
                            binding.setErrorSecurity(getString(R.string.security_success_change));
                        }
                        binding.btnSecurity.setEnabled(true);
                    }
                });
            } else {
                binding.setErrorSecurity(getResources().getString(R.string.security_short));
                binding.btnSecurity.setEnabled(true);
            }
        } else {
            binding.setErrorSecurity(getResources().getString(R.string.wrong_password));
            binding.btnSecurity.setEnabled(true);
        }

    }

    private void postNewPassword() {
        String pass1 = binding.oldPassword.getText().toString().trim();
        String pass2 = binding.newPassword.getText().toString().trim();
        String pass3 = binding.confirmPassword.getText().toString().trim();
        final String oldPassword = manager.password();
        if (!oldPassword.equals(pass1)){
            binding.setErrorChangePassword(getString(R.string.wrong_password));
            return;
        }
        binding.btnChangePassword.setEnabled(false);
        vmSignConditions.changePasswordConditions(pass1, pass2, pass3).observe(getViewLifecycleOwner(), user -> {
            if (!user.getError()) {
                vmSettingAuth.changePassword(pass1, pass2).observe(getViewLifecycleOwner(), cash -> {
                    if (!cash.getError()) {
                        manager.setPassword(pass2);
                        binding.setErrorChangePassword(cash.getMessage());
                        binding.oldPassword.setText("");
                        binding.newPassword.setText("");
                        binding.confirmPassword.setText("");
                    } else {
                        binding.setVisiblePassword(false);
//                        requireActivity().onBackPressed();
                    }
                    binding.btnChangePassword.setEnabled(true);
                });
            } else {
                binding.btnChangePassword.setEnabled(true);
                binding.setErrorChangePassword(user.getMessage());
            }
        });

    }

    private void postStore() {
        vmStore.postStore(getStore1()).observe(getViewLifecycleOwner(), store -> {
            editStore = false;
            if (store.getError()) {
                binding.setErrorStore(store.getMessage());
            }
        });
    }

    private Store getStore1() {
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
            if (store != null && !store.getError() && editStore) {
                binding.setModel(store);
            }
        });
    }

    private AlertDialog.Builder dialogForgetPassword() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        EditText email = new EditText(dialog.getContext());
        dialog.setTitle("Forget Password")
                .setMessage("enter your email")
                .setView(email)
                .setPositiveButton("OK", (dialog1, which) -> {
                    sendEmail(dialog1, email.getText().toString().trim());
                })
                .setNegativeButton("CANCEL", (dialog1, which) -> {
                    dialog1.dismiss();
                });
        return dialog;
    }

    private void sendEmail(DialogInterface dialog1, String email) {
        vmSettingAuth.forgetPassword(email)
                .observe(getViewLifecycleOwner(), cash -> {
                    if (!cash.getError()) {
                        dialog1.dismiss();
                    } else {
                        Toast.makeText(requireContext(), cash.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}