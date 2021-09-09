package soft.mahmod.yourreceipt.view_fragment.edit_account;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.conditions.edit_account.ChangePassword;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.databinding.FragmentChangePasswordBinding;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.view_model.user_account.VMAuthReg;
import soft.mahmod.yourreceipt.view_model.user_account.VMEditAccount;

public class FragmentChangePassword extends Fragment {
    private FragmentChangePasswordBinding binding;
    private static final String TAG = "FragmentChangePassword";
    private VMAuthReg vmAuthReg;
    private ActivityIntent intent;
    private VMEditAccount account;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = ActivityIntent.getInstance(requireContext());
        vmAuthReg = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMAuthReg.class);
        account = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMEditAccount.class);

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
    @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_change_password,container,false);
        binding.btnDown.setOnClickListener(v -> {
            String pass1 = binding.oldPassword.getText().toString().trim();
            String pass2 = binding.newPassword.getText().toString().trim();
            ChangePassword changePassword = new ChangePassword();
            if (changePassword.changePassword(pass1, pass2)) {
                Toast.makeText(requireContext(),
                        changePassword.errorChangePassword(), Toast.LENGTH_SHORT).show();
            } else {
                dialogChangePassword(pass1, pass2);
            }
            Log.d(TAG, "onClick: "+changePassword.errorChangePassword());
        });
        return binding.getRoot();
    }
    private void dialogChangePassword(String pass1, String pass2) {
        DialogConfirm dialogConfirm = new DialogConfirm(requireContext(), new DialogListener() {
            @Override
            public void clickCancel(DialogInterface dialog) {
                dialog.dismiss();
            }

            @Override
            public void clickOk(DialogInterface dialog) {
                dialog.dismiss();
                changePassword(pass1, pass2);
            }
        });
        dialogConfirm.createDialog("change password", "Are you Shore");
        dialogConfirm.listenerDialog();
        dialogConfirm.showDialog();
    }

    private void changePassword(String pass1, String pass2) {
        account.changePassword(pass1, pass2).observe(getViewLifecycleOwner(), cash -> {
            if (cash.getError()) {
                Toast.makeText(requireContext(), cash.getMessage(), Toast.LENGTH_SHORT).show();
            } else intent.userMakeChange(requireActivity());
        });
    }
}
