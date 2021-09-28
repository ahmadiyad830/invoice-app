package soft.mahmod.yourreceipt.view_fragment.registration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentSignInBinding;
import soft.mahmod.yourreceipt.view_model.auth.SettingAuth;
import soft.mahmod.yourreceipt.view_model.auth.VMSignIn;
import soft.mahmod.yourreceipt.view_model.database.VMUser;


public class FragmentSignIn extends Fragment {
    private static final String TAG = "FragmentSignIn";
    private FragmentSignInBinding binding;
    private VMSignIn vmSignIn;
    private VMUser vmDbUser;
    private SettingAuth vmSettingAuth;
    private NavController controller;
    private ActivityIntent intent;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmSignIn = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMSignIn.class);
        // FIXME: 9/22/2021  reblace with repo database package
        vmDbUser = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(VMUser.class);
        vmSettingAuth = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication())).get(SettingAuth.class);


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
        vmSettingAuth.forgetPassword(email)
                .observe(getViewLifecycleOwner(),cash -> {
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
        intent = ActivityIntent.getInstance(requireContext());
        controller = Navigation.findNavController(view);
        FragmentSignInArgs argsEmail =FragmentSignInArgs.fromBundle(getArguments());
        if (argsEmail!=null){
            binding.email.setText(argsEmail.getArgsEmail());
        }
        binding.txtGoSignup.setOnClickListener(v -> {
            controller.navigate(FragmentSignInDirections.actionFragmentSignInToFragmentSignUp());
        });
        binding.btnSignup.setOnClickListener(v -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();
            ConditionsSignIn conditionsSignIn = new ConditionsSignIn(email, password);
            if (conditionsSignIn.getError()) {
                binding.setError(conditionsSignIn.getMessage());
            } else if (vmSignIn.isConnection()) {
                signIn(email, password);
            } else {
                binding.setError(getResources().getString(R.string.items));
            }

        });
    }

    private void signIn(String email, String password) {
        binding.setProgress(true);
        vmSignIn.signIn(email, password)
                .observe(getViewLifecycleOwner(), user -> {
                    if (!user.getError()) {
                        intent.userSign(requireActivity());
                    } else {
                        binding.setError(user.getMessage());
                    }
                    binding.setProgress(false);
                });
    }

}