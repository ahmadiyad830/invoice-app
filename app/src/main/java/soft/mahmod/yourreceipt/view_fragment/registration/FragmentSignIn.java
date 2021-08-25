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
import soft.mahmod.yourreceipt.conditions.catch_registration.ConditionsSignIn;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentSignInBinding;
import soft.mahmod.yourreceipt.view_model.registration.VMAuthReg;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSignIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSignIn extends Fragment {
    private static final String TAG = "FragmentSignIn";
    private FragmentSignInBinding binding;
    private VMAuthReg vmAuthReg;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmAuthReg = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMAuthReg.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.txtGoSignup.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_fragmentSignIn_to_fragmentSignUp);
        });
        binding.btnSignup.setOnClickListener(v -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();
            ConditionsSignIn conditionsSignIn = new ConditionsSignIn(email, password);
            if (conditionsSignIn.getError()){
                binding.setError(conditionsSignIn.getMessage());
                return;
            }
            signIn(email, password);
        });
    }

    private void signIn(String email, String password) {
        binding.setProgress(true);
        vmAuthReg.signIn(email,password);
        vmAuthReg.getErrorData().observe(getViewLifecycleOwner(), s -> {
            Log.d(TAG, "signIn: "+s);
            if (s.equals("true")){
                vmAuthReg.getData().observe(getViewLifecycleOwner(),firebaseUser -> {
                    binding.setProgress(false);
                    Log.d(TAG, "signIn: "+firebaseUser.getEmail());
                    SessionManager manager = SessionManager.getInstance(requireContext());
                    manager.userSignIn();
                    binding.setError(s);
                });
            }
            binding.setProgress(false);
            binding.setError(s);
        });
    }

}