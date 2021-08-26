package soft.mahmod.yourreceipt.view_fragment.registration;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.conditions.catch_registration.ConditionsSignIn;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentSignInBinding;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.view_model.registration.VMAuthReg;
import soft.mahmod.yourreceipt.view_model.registration.VMDbUser;


public class FragmentSignIn extends Fragment {
    private static final String TAG = "FragmentSignIn";
    private FragmentSignInBinding binding;
    private VMAuthReg vmAuthReg;
    private VMDbUser vmDbUser;
    private NavController controller;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmAuthReg = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMAuthReg.class);
        vmDbUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMDbUser.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        binding.txtForgetPassword.setOnClickListener(v -> {
            createDialog().show();
        });
        return binding.getRoot();
    }

    private AlertDialog.Builder createDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        EditText email = new EditText(dialog.getContext());
        dialog.setTitle("Forget Password")
                .setMessage("enter your email")
                .setView(email)
                .setPositiveButton("OK", (dialog1, which) -> {
                    sendEmail(dialog1,email.getText().toString().trim());
                })
                .setNegativeButton("CANCEL", (dialog1, which) -> {
                    dialog1.dismiss();
                });
        return dialog;
    }

    private void sendEmail(DialogInterface dialog1, String email) {
        vmAuthReg.forgetPassword(email);
        vmAuthReg.getErrorData().observe(getViewLifecycleOwner(),cash -> {
            if (!cash.getError()){
                dialog1.dismiss();
            }else {
                Toast.makeText(requireContext(), cash.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        binding.txtGoSignup.setOnClickListener(v -> {
            controller.navigate(FragmentSignInDirections.actionFragmentSignInToFragmentSignUp());
        });
        binding.btnSignup.setOnClickListener(v -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();
            ConditionsSignIn conditionsSignIn = new ConditionsSignIn(email, password);
            if (conditionsSignIn.getError()) {
                binding.setError(conditionsSignIn.getMessage());
                return;
            }
            signIn(email, password);
        });
    }

    private void signIn(String email, String password) {
        binding.setProgress(true);
        vmAuthReg.signIn(email, password);
        vmAuthReg.getErrorData().observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()) {
              checkIsActive();
            } else {
                binding.setError(cash.getMessage());
            }
        });
        binding.setProgress(false);
    }

    private void checkIsActive() {
        vmDbUser.getErrorData().observe(getViewLifecycleOwner(), cash1 -> {
            if (cash1.getError()) {
                binding.setProgress(false);
                binding.setError(cash1.getMessage());
            } else {
                vmDbUser.getData().observe(getViewLifecycleOwner(), user -> {
                    if (user.isActive() || user.isBlock()) {
                        SessionManager manager = SessionManager.getInstance(requireContext());
                        manager.userSignIn(requireActivity());
                    } else {
                        controller.navigate(FragmentSignInDirections.actionFragmentSignInToFragmentActive());
                    }
                });
            }
            Log.d(TAG, "signIn: 3"+cash1);
            binding.setError(cash1.getMessage());
        });
        binding.setProgress(false);
    }
//ahmadiyad88@yahoo.com

}