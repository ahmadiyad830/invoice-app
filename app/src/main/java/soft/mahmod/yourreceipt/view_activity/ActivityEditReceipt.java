package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ActivityEditReceiptBinding;

public class ActivityEditReceipt extends AppCompatActivity {
    private static final String TAG = "ActivityEditReceipt";
    private ActivityEditReceiptBinding binding;
    private NavController controller;
    private final Activity context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_receipt);
        controller = Navigation.findNavController(context,R.id.nav_edit_receipt_continer);
        controller.addOnDestinationChangedListener((controller1, destination, arguments) -> {
            Log.d(TAG, "onCreate: "+destination.getLabel());
        });
    }
}