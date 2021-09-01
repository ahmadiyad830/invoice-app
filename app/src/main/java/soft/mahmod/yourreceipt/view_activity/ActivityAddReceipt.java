package soft.mahmod.yourreceipt.view_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ActivityAddReceiptBinding;
import soft.mahmod.yourreceipt.databinding.ActivityDetailsBinding;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;

public class ActivityAddReceipt extends AppCompatActivity {
    private ActivityAddReceiptBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_receipt);



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        getViewModelStore().clear();
    }
}