package soft.mahmod.yourreceipt.view_fragment.registration;

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
import soft.mahmod.yourreceipt.databinding.FragmentSignInBinding;
import soft.mahmod.yourreceipt.databinding.FragmentSignUpBinding;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.view_model.VMSignUp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSignUp extends Fragment {
    private FragmentSignUpBinding binding;
    private VMSignUp viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMSignUp.class);
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
            String password = binding.password.getText().toString().trim();
            User user = new User(email, password);
            viewModel.signUp(user).observe(getViewLifecycleOwner(), cash -> {
                if (cash.getError()) {
                    binding.setError(cash.getMessage());
                }else {
                    SessionManager manager = SessionManager.getInstance(requireContext());
                    manager.setUser(user);
                    manager.userSignIn(user);
                    requireActivity().finish();
                }
            });
        });
    }
}