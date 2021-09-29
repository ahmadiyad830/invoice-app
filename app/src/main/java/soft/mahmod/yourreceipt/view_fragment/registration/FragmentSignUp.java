package soft.mahmod.yourreceipt.view_fragment.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.databinding.FragmentSignUpBinding;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.statics.ApiURLS;
import soft.mahmod.yourreceipt.view_model.auth.VMSignUp;
import soft.mahmod.yourreceipt.view_model.condition.VMSignConditions;
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
    private VMSignUp vmSignUp;
    private NavController controller;
    private VMSignConditions conditions;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMUser.class);

        vmSignUp = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMSignUp.class);

        conditions = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMSignConditions.class);
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
        ActivityIntent intent = ActivityIntent.getInstance(requireContext());
        binding.txtGoSignin.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        binding.btnSignup.setOnClickListener(v -> {
            String email = binding.email.getText().toString().trim();
            String pass1 = binding.password.getText().toString().trim();
            String pass2 = binding.passwordConfig.getText().toString().trim();
            String key = binding.edtSecurity.getText().toString().trim();
            if (key.length() != 4) {
                binding.setError(getResources().getString(R.string.add_security));
                return;
            }
            conditions.signUpCondition(email, pass1, pass2).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    if (user.getError()) {
                        binding.setError(user.getMessage());
                    } else {
                        signUp(email, pass1);
                    }
                }

            });
        });
    }

    private void signUp(String email, String pass1) {
        binding.setProgress(true);
        vmSignUp.signIn(email, pass1)
                .observe(getViewLifecycleOwner(), user -> {
                    if (!user.getError()) {
                        uploadUser(email, pass1, user.getUid());
                    } else {
                        binding.setProgress(false);
                        binding.setError(user.getMessage());
                    }
                });

    }

    private void uploadUser(String email, String pass1,String uid) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(pass1);
        user.setUid(uid);
        String key = binding.edtSecurity.getText().toString();
        user.setSecurity(key);
        vmUser.postUser(user).observe(getViewLifecycleOwner(), cash -> {
            if (cash.getError()) {
                binding.setError(cash.getMessage());
            } else {
                FragmentSignUpDirections.ActionFragmentSignUpToFragmentInfo passEmail =
                        FragmentSignUpDirections.actionFragmentSignUpToFragmentInfo();
                passEmail.setArgsEmail(email);
                try {
                    controller.navigate(passEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            binding.setProgress(false);
        });
    }
}