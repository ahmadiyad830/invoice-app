package soft.mahmod.yourreceipt.view_fragment.main;

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

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentSettingBinding;
import soft.mahmod.yourreceipt.model.Setting;
import soft.mahmod.yourreceipt.view_activity.MainActivity;
import soft.mahmod.yourreceipt.view_model.auth_user.VMAuthReg;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentSetting";
    private FragmentSettingBinding binding;
    private VMAuthReg vmAuthReg;
    private SessionManager manager;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = SessionManager.getInstance(requireContext());
        vmAuthReg = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMAuthReg.class);
    }

    private final Setting setting = new Setting();

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

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (binding.btnSignout.getId() == id) {
            vmAuthReg.signOut();
            manager.userSignOut(requireActivity());
        } else if (binding.edtAccount.getId() == id) {
            binding.setIsEditAccount(!binding.getIsEditAccount());
            binding.setIsChangPassword(!binding.getIsChangPassword());
        } else if (binding.txtChangePassword.getId() == id) {
            binding.setIsChangPassword(binding.getIsEditAccount());
//            binding.setIsChangPassword(binding.getIsChangPassword() || !binding.getIsEditAccount());
        } else if (binding.txtStore.getId() == id) {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_menu_setting_to_fragmentEditAccount);
        }
    }
}