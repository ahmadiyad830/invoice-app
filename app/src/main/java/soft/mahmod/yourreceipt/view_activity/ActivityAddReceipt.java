package soft.mahmod.yourreceipt.view_activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.common.Common;
import soft.mahmod.yourreceipt.databinding.ActivityAddReceiptBinding;
import soft.mahmod.yourreceipt.view_fragment.add_receipt.tab_add_products.FragmentAddProducts;

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
        Common.listProduct.clear();
        binding = null;
        getViewModelStore().clear();
    }
}