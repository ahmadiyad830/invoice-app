package soft.mahmod.yourreceipt.view_fragment.registration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.conditions.catch_registration.ConditionsSignIn;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.databinding.FragmentSignInBinding;
import soft.mahmod.yourreceipt.view_model.auth.SignIn;
import soft.mahmod.yourreceipt.view_model.database.VMUser;


public class FragmentSignIn extends Fragment {
    private static final String TAG = "FragmentSignIn";
    private FragmentSignInBinding binding;
    private SignIn signIn;
    private VMUser vmDbUser;
    private NavController controller;
    private ActivityIntent intent;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signIn = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(SignIn.class);
        // FIXME: 9/22/2021  reblace with repo database package
        vmDbUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMUser.class);

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
        // FIXME: 9/22/2021 create method in setting repo forget password
//        vmAuthReg.forgetPassword(email);
//        vmAuthReg.getErrorData().observe(getViewLifecycleOwner(),cash -> {
//            if (!cash.getError()){
//                dialog1.dismiss();
//            }else {
//                Toast.makeText(requireContext(), cash.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intent = ActivityIntent.getInstance(requireContext());
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
        if (signIn.isConnection()) {
            signIn.signIn(email, password)
                    .observe(getViewLifecycleOwner(), user -> {
                        Log.d(TAG, "signIn: " + user.toString());
                        if (!user.getError()) {
                        intent.userSignIn(requireActivity());
                        } else {
                            binding.setError(user.getMessage());
                        }
                    });
        }
    }

}