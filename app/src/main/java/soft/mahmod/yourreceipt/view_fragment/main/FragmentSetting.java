package soft.mahmod.yourreceipt.view_fragment.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentSettingBinding;
import soft.mahmod.yourreceipt.view_model.VMChangePassword;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentSetting";
    private FragmentSettingBinding binding;
    private SessionManager manager;
    private VMChangePassword vmChangePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        vmChangePassword = new ViewModelProvider(
                getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        )
        ).get(VMChangePassword.class);
        binding.btnSignout.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = SessionManager.getInstance(requireContext());
        binding.edtAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (binding.btnSignout.getId() == id) {
            manager.userSignOut(requireActivity());
        } else if (binding.edtAccount.getId() == id) {

//            Log.d(TAG, "onClick: click");
//            String newPass = "1234";
//            if (manager.hasNewPass(newPass)) {
//                vmChangePassword.changePassword(manager.getUser().getEmail(), newPass)
//                        .observe(getViewLifecycleOwner(), cash -> {
//                            Log.d(TAG, "onClick: "+cash.toString());
//                        });
//            }

        }
    }
}