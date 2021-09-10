package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class FragmentAddReceipt extends Fragment implements DatabaseUrl {

    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    public static final int REQUEST_CAMERA = 0x82317354;
    private final List<String> listWarning = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_receipt, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnNext.setOnClickListener(v -> {
            setReceipt();
        });
    }


    private void setReceipt() {
        FragmentAddReceiptDirections.ActionAddReceiptToPrintReceipt argsToClient
                = FragmentAddReceiptDirections.actionAddReceiptToPrintReceipt();
        argsToClient.setReceiptToPrint(getReceipt());
        Navigation.findNavController(requireView()).navigate(argsToClient);
//        Log.d(TAG, "onViewCreated: " + argsToClient.getReceiptToClient().toString());
    }

    private Receipt getReceipt() {
        Receipt model = new Receipt();
        model.setSubject(binding.edtSubject.getText().toString().trim());
        model.setNote(binding.edtNote.getText().toString().trim());
        model.setType(binding.switchMaterial.isChecked() ? binding.switchMaterial.getTextOff().toString()
                : binding.switchMaterial.getTextOn().toString());
        return model;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        getViewModelStore().clear();
    }


}