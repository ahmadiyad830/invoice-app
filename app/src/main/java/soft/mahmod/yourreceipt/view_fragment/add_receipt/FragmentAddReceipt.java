package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARPayment;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.view_model.database.VMItems;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;

public class FragmentAddReceipt extends Fragment implements DatabaseUrl, AdapterView.OnItemSelectedListener, ARPayment.ListenerOnClick, DialogListener {

    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    private final List<Payment> listPayment = new ArrayList<>();
    private VMReceipt vmReceipt;
    private String[] typeReceipt;
    private ARPayment adapter;
    private String receiptType;
    private VMItems vmItems;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmReceipt = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMReceipt.class);
        vmItems = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMItems.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_receipt, container, false);

        typeReceipt = new String[]{getResources().getString(R.string.paid)
                , getResources().getString(R.string.debt), getResources().getString(R.string.bayment)};

        adapter = new ARPayment(listPayment, this);

        init();
        binding.btnAdd.setOnClickListener(this::onClickAdd);
        binding.btnDelete.setOnClickListener(this::onClickDeleteAll);
        binding.btnNext.setOnClickListener(v -> {
            setReceipt();
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
        binding.editDate.setOnClickListener(v -> {
            loadCalenderDialog();
        });
    }

    private void loadCalenderDialog() {
        DialogConfirm dialog = new DialogConfirm(requireContext(), this);
        dialog.addView(createCalendar(dialog.context()));
        dialog.listenerDialog();
        dialog.showDialog();
    }


    private CalendarView createCalendar(Context context) {
        CalendarView calendarView = new CalendarView(context);
        calendarView.setMinDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            binding.editDate.setText(date);
        });
        return calendarView;
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
        model.setPayment(getPayment());
        vmReceipt.postReceipt(model).observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()) {
                vmItems.updatesQuantity(getIds(model.getProducts())
                        ,getItemQuantitys(model.getProducts()),getQuantitys(model.getProducts()));
                requireActivity().finish();
            }
        });
    }
    private List<Double> getItemQuantitys(List<Products> products) {
        List<Double> quantits = new ArrayList<>();
        for (Products model : products) {
            quantits.add(model.getItemQuantity());
        }
        return quantits;
    }
    private List<Double> getQuantitys(List<Products> products) {
        List<Double> quantits = new ArrayList<>();
        for (Products model : products) {
            quantits.add(model.getQuantity());
        }
        return quantits;
    }

    private List<String> getIds(List<Products> products) {
        List<String> ids = new ArrayList<>();
        for (Products model : products) {
            ids.add(model.getItemId());
        }
        return ids;
    }
    private Receipt getReceipt() {
        Receipt model = new Receipt();
        model.setSubject(binding.edtSubject.getText().toString().trim());
        model.setNote(binding.edtNote.getText().toString().trim());
        return model;
    }

    private Payment getPayment() {
        Payment payment = new Payment();
        if (receiptType.equals(typeReceipt[0])) {
            payment.setTypePayment(typeReceipt[0]);
        } else if (receiptType.equals(typeReceipt[1])) {
            payment = new Payment("2021/9/16");
            payment.setTypePayment(typeReceipt[1]);
        } else if (receiptType.equals(typeReceipt[2])) {
            payment = new Payment(listPayment);
            payment.setTypePayment(typeReceipt[2]);
        }
        return payment;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        receiptType = (String) parent.getItemAtPosition(position);
        if (receiptType.equals(typeReceipt[0])){
            binding.setIsBayment(false);
            binding.setIsDept(false);
        }else if (receiptType.equals(typeReceipt[1])){
            binding.setIsBayment(false);
            binding.setIsDept(true);
        }else if (receiptType.equals(typeReceipt[2])){
            binding.setIsBayment(true);
            binding.setIsDept(false);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        receiptType = typeReceipt[0];
    }


    @Override
    public void payment(Payment model) {

    }

    @Override
    public void deletePayment(int position) {
        listPayment.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void paid(boolean isChecked, int position) {
        listPayment.get(position).setPaid(isChecked);
    }

    private void onClickAdd(View v) {
        double price = 0;
        try {
            price = Double.parseDouble(binding.editPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            price = 0;
        }
        Payment payment = new Payment();
        payment.setDate(binding.editDate.getText().toString().trim());
        payment.setPrice(price);
        listPayment.add(payment);
        adapter.notifyItemInserted(listPayment.size() - 1);
    }

    private void onClickDeleteAll(View v) {
        if (listPayment.size() > 0) {
            adapter.notifyItemRangeRemoved(0, listPayment.size());
            listPayment.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        getViewModelStore().clear();
    }

    @Override
    public void clickOk(DialogInterface dialog) {

    }

    @Override
    public void clickCancel(DialogInterface dialog) {
        dialog.dismiss();
    }
}