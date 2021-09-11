package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;

public class FragmentAddReceipt extends Fragment implements DatabaseUrl {

    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    public static final int REQUEST_CAMERA = 0x82317354;
    private final List<String> listWarning = new ArrayList<>();
    private VMReceipt vmReceipt;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmReceipt = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMReceipt.class);
    }

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
        FragmentAddReceiptArgs argsReceipt = FragmentAddReceiptArgs.fromBundle(getArguments());
        Receipt model = argsReceipt.getReceiptToAddReceipt();
        model.setSubject(getReceipt().getSubject());
        model.setNote(getReceipt().getNote());
        model.setType(getReceipt().getType());
        vmReceipt.postReceipt(model).observe(getViewLifecycleOwner(),cash -> {
            if (!cash.getError()){
                FragmentAddReceiptDirections.ActionAddReceiptToPrintReceipt argsToClient
                        = FragmentAddReceiptDirections.actionAddReceiptToPrintReceipt();
                argsToClient.setReceiptToPrint(model);
                Navigation.findNavController(requireView()).navigate(argsToClient);
            }
            Log.d(TAG, "setReceipt: "+cash.getMessage());
            Log.d(TAG, "setReceipt: \n"+model.toString());
        });
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