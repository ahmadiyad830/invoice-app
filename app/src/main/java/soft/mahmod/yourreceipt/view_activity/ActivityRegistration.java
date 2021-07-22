package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.ActivityRegistrationBinding;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.view_model.VMSignIn;

public class ActivityRegistration extends AppCompatActivity {
    private static final String TAG = "ActivityRegistration";
    private VMSignIn viewModel;
    private ActivityRegistrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registration);
        viewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(VMSignIn.class);

        binding.btnSignup.setOnClickListener(v -> {
            viewModel.signIn(binding.email.getText().toString().trim(),binding.password.getText().toString().trim())
                    .observe(this,user -> {
                        SessionManager manager = SessionManager.getInstance(this);
                        manager.userSignIn(user);
                        Log.d(TAG, "onCreate: "+user.toString());
                    });
        });


    }
}