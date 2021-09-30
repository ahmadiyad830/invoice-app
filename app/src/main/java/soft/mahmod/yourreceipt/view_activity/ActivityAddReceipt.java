package soft.mahmod.yourreceipt.view_activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.common.Common;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.controller.SecurityManager;
import soft.mahmod.yourreceipt.databinding.ActivityAddReceiptBinding;
import soft.mahmod.yourreceipt.dialog.DialogSecurity;

public class ActivityAddReceipt extends AppCompatActivity {
    private static final String TAG = "ActivityAddReceipt";
    private ActivityAddReceiptBinding binding;
    private DialogSecurity dialogConfirm;
    private SecurityManager manager;
    private String keySec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_receipt);

        dialogConfirm = new DialogSecurity(this, getLayoutInflater());
        manager= SecurityManager.getInectance(this);

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
    public void onBackPressed() {
        super.onBackPressed();
//        ActivityIntent.getInstance(this).userMakeChange(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Common.listProduct.clear();
        binding = null;
        getViewModelStore().clear();
    }
}