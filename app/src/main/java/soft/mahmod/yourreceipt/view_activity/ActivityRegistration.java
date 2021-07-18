package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.view_model.VMSignIn;

public class ActivityRegistration extends AppCompatActivity {
    private VMSignIn viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(VMSignIn.class);
        viewModel.signIn("ahmadiyad@gmail.com","123456").observe(this,user -> {
            Log.d("ActivityRegistration", "onCreate: "+"message_error"+user.getError()+"\n"+user.getMessage());
        });
    }
}