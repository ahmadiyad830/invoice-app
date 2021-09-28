package soft.mahmod.yourreceipt.view_fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentSettingBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment implements View.OnClickListener {
    FragmentSettingBinding binding;

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
        binding.txtVisiblePassword.setOnClickListener(this);
        binding.txtVisibleStore.setOnClickListener(this);
        binding.txtVisibleSeurity.setOnClickListener(this);
        binding.txtForgetPassword.setOnClickListener(this);
        binding.btnChangePassword.setOnClickListener(this);
        binding.btnEditStore.setOnClickListener(this);


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
        }else if (id == binding.txtVisibleSeurity.getId()){
            binding.setVisibleSecurity(true);
            binding.setVisiblePassword(!binding.getVisibleSecurity());
            binding.setVisibleStore(!binding.getVisibleSecurity());
        }
        else if(id==binding.btnEditStore.getId()){

        }else if (id == binding.btnChangePassword.getId()){

        }else if(id == binding.txtForgetPassword.getId()){

        }
    }
}