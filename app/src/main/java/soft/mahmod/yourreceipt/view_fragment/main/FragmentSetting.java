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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (binding.edtAccount.getId() == id) {

        } else if (binding.edtAccount.getId() == id) {
            binding.setIsEditAccount(!binding.getIsEditAccount());
        } else if (binding.txtChangePassword.getId() == id) {
            binding.setIsChangPassword(!binding.getIsChangPassword());
        } else if (binding.txtStore.getId() == id) {

        }
    }


}