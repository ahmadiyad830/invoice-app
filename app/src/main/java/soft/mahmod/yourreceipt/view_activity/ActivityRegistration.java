package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.ActivityRegistrationBinding;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.view_model.VMSignIn;

public class ActivityRegistration extends AppCompatActivity {
    private static final String TAG = "ActivityRegistration";
    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);

        NavController controller = Navigation.findNavController(this, R.id.fragmentContainerView2);
        controller.addOnDestinationChangedListener((controller1, destination, arguments) -> {
            if (destination.getLabel() != null) {
                setTitle(destination.getLabel().toString());
            }
        });

    }

}