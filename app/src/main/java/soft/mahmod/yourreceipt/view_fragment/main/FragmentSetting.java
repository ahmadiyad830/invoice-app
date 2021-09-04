package soft.mahmod.yourreceipt.view_fragment.main;

import android.content.DialogInterface;
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
import android.widget.Toast;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.conditions.edit_account.ChangePassword;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentSettingBinding;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.view_model.user_account.VMAuthReg;
import soft.mahmod.yourreceipt.view_model.user_account.VMEditAccount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentSetting";
    private FragmentSettingBinding binding;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        binding.btnSignout.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setIsEditAccount(false);
        binding.setIsChangPassword(false);
        binding.edtAccount.setOnClickListener(this);
        binding.txtChangePassword.setOnClickListener(this);
        binding.txtStore.setOnClickListener(this);
        binding.btnDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (binding.btnSignout.getId() == id) {
            DialogConfirm dialog = new DialogConfirm(requireContext());
            dialog.listenerDialog();
            dialog.setDialogListener(new DialogListener() {
                @Override
                public void clickOk(DialogInterface dialog) {
                    vmAuthReg.signOut();
                    intent.userSignOut(requireActivity());
                    dialog.dismiss();
                }

                @Override
                public void clickCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
            dialog.createDialog("Sign out", "Are You shure");
            dialog.showDialog();
        } else if (binding.edtAccount.getId() == id) {
            binding.setIsEditAccount(!binding.getIsEditAccount());
        } else if (binding.txtChangePassword.getId() == id) {
            binding.setIsChangPassword(!binding.getIsChangPassword());
        } else if (binding.btnDown.getId() == id) {
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

        } else if (binding.txtStore.getId() == id) {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_menu_setting_to_fragmentEditAccount);
        }
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