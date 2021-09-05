package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.view_fragment.edit_account.FragmentEditAccount;

public class FragmentAddReceipt extends Fragment implements DatabaseUrl {

    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    public static final int REQUEST_CAMERA = 0x82317354;
    private final List<String> listWarning = new ArrayList<>();
    private Uri uri = Uri.EMPTY;

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
        if (!uri.toString().isEmpty()) {
            binding.setHasImage(true);
            binding.setInvoice(uri.toString());
        }

        binding.btnNext.setOnClickListener(v -> {
            if (warningReceipt())
                dialogWarning();
            else setReceipt();
        });
    }


    private void setReceipt() {
        FragmentAddReceiptDirections.ActionFragmentAddReceiptToFragmentAddClient5 argsToClient
                = FragmentAddReceiptDirections.actionFragmentAddReceiptToFragmentAddClient5();
        argsToClient.setReceiptToClient(getReceipt());
        Navigation.findNavController(requireView()).navigate(argsToClient);
        Log.d(TAG, "onViewCreated: " + argsToClient.getReceiptToClient().toString());
    }

    private Receipt getReceipt() {
        Receipt model = new Receipt();
        model.setInvoice(uri.toString());
        model.setSubject(binding.edtSubject.getText().toString().trim());
        model.setNote(binding.edtNote.getText().toString().trim());
        model.setType(binding.switchMaterial.isChecked() ? binding.switchMaterial.getTextOff().toString()
                : binding.switchMaterial.getTextOn().toString());
        return model;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.btnCamera.setOnClickListener(v -> {
            openCamera();
        });
        binding.logo.setOnClickListener(v -> {
            FragmentAddReceiptDirections.ActionFragmentAddReceiptToFragmentZoomImage zoomImage
                    = FragmentAddReceiptDirections.actionFragmentAddReceiptToFragmentZoomImage();
            zoomImage.setUrlToZoom(uri.toString());
            Navigation.findNavController(requireView()).navigate(zoomImage);
        });
    }
    public void openCamera() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, FragmentEditAccount.IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FragmentEditAccount.IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            binding.setHasImage(true);
            uri = data.getData();
            binding.setInvoice(uri.toString());

        }
    }

    private boolean warningReceipt() {
        if (getReceipt().getInvoice().equals("")) {
            listWarning.add(getResources().getString(R.string.invoice));
        }
        if (getReceipt().getSubject().equals("")) {
            listWarning.add(getResources().getString(R.string.subject));
        }
        return listWarning.size() > 0;
    }

    private void dialogWarning() {
        DialogConfirm dialogConfirm = new DialogConfirm(requireContext());
        dialogConfirm.setDialogListener(new DialogListener() {
            @Override
            public void clickOk(DialogInterface dialog) {
                listWarning.clear();
                setReceipt();
            }

            @Override
            public void clickCancel(DialogInterface dialog) {
                dialog.dismiss();
                listWarning.clear();
            }
        });
        dialogConfirm.listenerDialog();
//        TODO translate
        dialogConfirm.setIcon(R.drawable.ic_twotone_warning_24);
        StringBuilder warning = new StringBuilder("\n");
        for (int i = 0; i < listWarning.size(); i++) {
            warning.append(listWarning.get(i)).append("\n");
        }

        dialogConfirm.createDialog(getResources().getString(R.string.warning),
                getResources().getString(R.string.warning_not_add)
                        + warning.toString()
        );
        dialogConfirm.showDialog();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        getViewModelStore().clear();
    }


}