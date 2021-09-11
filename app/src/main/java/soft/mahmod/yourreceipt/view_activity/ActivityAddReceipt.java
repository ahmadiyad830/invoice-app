package soft.mahmod.yourreceipt.view_activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ActivityAddReceiptBinding;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.view_fragment.add_receipt.FragmentAddItem;
import soft.mahmod.yourreceipt.view_model.ui.VMHelpMessage;

public class ActivityAddReceipt extends AppCompatActivity {
    private static final String TAG = "ActivityAddReceipt";
    private ActivityAddReceiptBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_receipt);

        NavController controller = Navigation.findNavController(this, R.id.fragment_container_add_receipt);
        controller.addOnDestinationChangedListener((controller1, destination, arguments) -> {
            binding.setNameFragment(destination.getLabel().toString());
            binding.progressHorizontalAddReceipt.setIndicatorColor();
            binding.progressHorizontalAddReceipt.setVisibility(View.VISIBLE);

            if (getResources().getString(R.string.client).trim().equals(destination.getLabel().toString())) {
                binding.helpMessage.setVisibility(View.GONE);
                binding.setProgress(35);
            } else if (getResources().getString(R.string.items).trim().equals(destination.getLabel().toString())) {
                binding.setProgress(65);
            } else if (getResources().getString(R.string.receipt).trim().equals(destination.getLabel().toString())) {
                binding.setProgress(90);
            } else {
                binding.progressHorizontalAddReceipt.setVisibility(View.GONE);
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentAddItem.listProduct.clear();
        binding = null;
        getViewModelStore().clear();
    }
}