package soft.mahmod.yourreceipt.view_fragment.registration;

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
import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.conditions.catch_registration.ConditionsSignUp;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.databinding.FragmentSignUpBinding;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.statics.ApiURLS;
import soft.mahmod.yourreceipt.view_model.user_account.VMAuthReg;
import soft.mahmod.yourreceipt.view_model.database.VMUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSignUp extends Fragment implements ApiURLS {
    private static final String TAG = "FragmentSignUp";
    private FragmentSignUpBinding binding;
    private VMUser vmUser;
    private VMAuthReg vmAuthReg;
    private NavController controller;
    private ActivityIntent intent;



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMUser.class);
        vmAuthReg = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMAuthReg.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        intent = ActivityIntent.getInstance(requireContext());
        binding.txtGoSignin.setOnClickListener(v -> {
            controller.navigate(FragmentSignUpDirections.actionFragmentSignUpToFragmentSignIn());
        });
        binding.btnSignup.setOnClickListener(v -> {
            String email = binding.email.getText().toString().trim();
            String pass1 = binding.password.getText().toString().trim();
            String pass2 = binding.passwordConfig.getText().toString().trim();
            ConditionsSignUp conditionsSignUp = new ConditionsSignUp(email, pass1, pass2);
            if (conditionsSignUp.getError()) {
                binding.setError(conditionsSignUp.getMessage());
                return;
            }
            signUp(email, pass1);
        });
    }

    private void signUp(String email, String pass1) {
        vmAuthReg.signUp(email, pass1);
        vmAuthReg.getErrorData().observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()){
                uploadUser(email,pass1);
            }
            Log.d(TAG, "signUp: "+cash.toString());
            binding.setError(cash.getMessage());
        });
    }

    private void uploadUser(String email, String pass1) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(pass1);
        vmUser.postUser(user);
    }
}