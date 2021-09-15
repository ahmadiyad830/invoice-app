package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARPayment;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;

public class FragmentAddReceipt extends Fragment implements DatabaseUrl, AdapterView.OnItemSelectedListener, ARPayment.ListenerOnClick {

    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    public static final int REQUEST_CAMERA = 0x82317354;
    private final List<String> listWarning = new ArrayList<>();
    private final List<Payment> listPayment = new ArrayList<>();
    private VMReceipt vmReceipt;
    private final String[] typeReceipt = {"paid", "debt", "bayment"};
    private String key = typeReceipt[0];
    private ARPayment adapter;
    private String receiptType ;
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
        adapter = new ARPayment(listPayment, this);
        init();
        binding.btnAdd.setOnClickListener(this::onClickAdd);
        binding.btnDelete.setOnClickListener(this::onClickDeleteAll);
        binding.btnNext.setOnClickListener(v -> {
//            setReceipt();

        });
        return binding.getRoot();
    }

    private void init() {
        binding.recItemsBeyment.setHasFixedSize(true);
        binding.recItemsBeyment.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerInit();
    }

    private void spinnerInit() {
        binding.spinnerTypeReceipt.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>
                (requireContext(), android.R.layout.simple_spinner_item, typeReceipt);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTypeReceipt.setAdapter(adapter);
    }

    private void setReceipt() {
        FragmentAddReceiptArgs argsReceipt = FragmentAddReceiptArgs.fromBundle(getArguments());
        Receipt model = argsReceipt.getReceiptToAddReceipt();
        model.setSubject(getReceipt().getSubject());
        model.setNote(getReceipt().getNote());
        model.setType(getReceipt().getType());
        vmReceipt.postReceipt(model).observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()) {
                FragmentAddReceiptDirections.ActionAddReceiptToPrintReceipt argsToClient
                        = FragmentAddReceiptDirections.actionAddReceiptToPrintReceipt();
                argsToClient.setReceiptToPrint(model);
                Navigation.findNavController(requireView()).navigate(argsToClient);
            }
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        receiptType = (String) parent.getItemAtPosition(position);
        if (receiptType.equals(typeReceipt[0])){
            binding.setIsBayment(false);
        }else if (receiptType.equals(typeReceipt[1])){
            binding.setIsBayment(false);
        }else if (receiptType.equals(typeReceipt[2])){
            binding.setIsBayment(true);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void payment(Payment model) {

    }

    @Override
    public void deletePayment(int position) {
        listPayment.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void onClickAdd(View v) {
        double price = 0;
        try {
            price = Double.parseDouble(binding.editPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            price = 0;
        }
        Payment payment = new Payment();
        payment.setDate(binding.editPrice.getText().toString().trim());
        payment.setPrice(price);
        listPayment.add(payment);
        adapter.notifyItemInserted(listPayment.size() - 1);
    }

    private void onClickDeleteAll(View v) {
        if (listPayment.size() > 0) {
            adapter.notifyItemRangeRemoved(0, listWarning.size());
            listPayment.clear();
        }
    }
}