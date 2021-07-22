package soft.mahmod.yourreceipt.view_fragment.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.conditions.ConditionsSignIn;
import soft.mahmod.yourreceipt.conditions.ConditionsSignUp;
import soft.mahmod.yourreceipt.databinding.FragmentInfoBinding;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.view_model.VMSignUp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInfo extends Fragment {

    private static final String TAG = "FragmentInfo456";
    private FragmentInfoBinding binding;
    private VMSignUp viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);
        viewModel = new ViewModelProvider
                (getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMSignUp.class);
        // FragmentInfoPharmacyArgs args = FragmentInfoPharmacyArgs.fromBundle(getArguments());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSignup.setOnClickListener(v -> {
            String storeName = binding.storeName.getText().toString();
            String phoneNum = binding.phoneNum.getText().toString();
            String storeAddress = binding.storeAddress.getText().toString();
            if (getArguments() != null) {
                FragmentInfoArgs args = FragmentInfoArgs.fromBundle(getArguments());
                User userArgs = args.getUserArgs();
                if (userArgs != null) {
                    User user = new User(userArgs.getEmail(), userArgs.getPassword(), storeName, phoneNum, storeAddress);
                    ConditionsSignUp conditionsSignUp = new ConditionsSignUp
                            (userArgs.getEmail(), userArgs.getPassword(), phoneNum, storeName, storeAddress);
                    if (conditionsSignUp.isMore()) {
                        viewModel.signUp(user).observe(getViewLifecycleOwner(), user1 -> {
                            Log.d(TAG, "onViewCreated: "+"is succe");
                        });
                    } else {
//                         TODO Handle error if error user input
                    }
                } else {
//                    TODO Handle error if user args null
                }
            } else {
//                TODO handle if getArguments() null
            }

        });

    }
}