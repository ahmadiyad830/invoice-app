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
import soft.mahmod.yourreceipt.conditions.ConditionsSignIn;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentSignInBinding;
import soft.mahmod.yourreceipt.view_model.VMSignIn;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSignIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSignIn extends Fragment {
    private static final String TAG = "FragmentSignIn";
    private FragmentSignInBinding binding;
    private VMSignIn vmSignIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        vmSignIn = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMSignIn.class);

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
            signIn(email, password);

        });
    }

    private void signIn(String email, String password) {
        ConditionsSignIn conditionsSignIn = new ConditionsSignIn(email, password);
        if (conditionsSignIn.isSignIn()) {
            vmSignIn.signIn(email, password).observe(getViewLifecycleOwner(), user -> {
                Log.d(TAG, "onViewCreated: " + user.toString());
                if (!user.getError()) {
                    SessionManager manager = SessionManager.getInstance(requireContext());
                    manager.setUser(user);
                    manager.userSignIn(user);
                    requireActivity().finish();
                    Log.d(TAG, "signInerror: "+user.getError());
                } else {
                    binding.setError(user.getMessage());
                }
            });
        } else {
            binding.setError(conditionsSignIn.error());
        }
    }

}