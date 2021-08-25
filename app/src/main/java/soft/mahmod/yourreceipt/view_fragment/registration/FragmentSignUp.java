package soft.mahmod.yourreceipt.view_fragment.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.conditions.catch_registration.ConditionsSignUp;
import soft.mahmod.yourreceipt.databinding.FragmentSignUpBinding;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.statics.ApiURLS;
import soft.mahmod.yourreceipt.view_model.registration.VMAuthReg;
import soft.mahmod.yourreceipt.view_model.registration.VMDbUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSignUp extends Fragment implements ApiURLS {
    private static final String TAG = "FragmentSignUp";
    private FragmentSignUpBinding binding;
    private VMDbUser vmDbUser;
    private VMAuthReg vmAuthReg;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmDbUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMDbUser.class);
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
        binding.txtGoSignin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_fragmentSignUp_to_fragmentSignIn);
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
        vmAuthReg.getErrorData().observe(getViewLifecycleOwner(), s -> {
            if (s.isEmpty()) {
                vmAuthReg.getData().observe(getViewLifecycleOwner(), firebaseUser -> {
                    if (firebaseUser != null) {
                        User user = new User(email, pass1, firebaseUser.getUid());
                        vmDbUser.newUser(user, USER_SIGN_IN + firebaseUser.getUid());
                        Log.d(TAG, "onViewCreated: set value");
                    } else Log.d(TAG, "onViewCreated: null");
                });
            }
            binding.setError(s);
        });
    }
}