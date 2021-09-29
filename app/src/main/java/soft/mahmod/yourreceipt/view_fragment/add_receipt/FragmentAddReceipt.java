package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARPayment;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.controller.SecurityManager;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.dialog.DialogConfirm;
import soft.mahmod.yourreceipt.dialog.DialogListener;
import soft.mahmod.yourreceipt.dialog.DialogSecurity;
import soft.mahmod.yourreceipt.listeners.ListenerPayment;
import soft.mahmod.yourreceipt.listeners.ListenerSecurityDialog;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_model.database.VMItems;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;

public class FragmentAddReceipt extends Fragment implements DatabaseUrl, AdapterView.OnItemSelectedListener,
        ListenerPayment, DialogListener, TextWatcher {
    private SecurityManager manager;
    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    private final List<Payment> listPayment = new ArrayList<>();
    private VMReceipt vmReceipt;
    private String[] typeReceipt;
    private ARPayment adapter;
    private String receiptType;
    private VMItems vmItems;
    private HandleTimeCount handleTimeCount;
    private FragmentAddReceiptArgs argsReceipt;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmReceipt = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMReceipt.class);
        vmItems = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMItems.class);
        handleTimeCount = new HandleTimeCount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_receipt, container, false);
        manager = SecurityManager.getInectance(requireContext());
        typeReceipt = new String[]{getResources().getString(R.string.paid)
                , getResources().getString(R.string.debt), getResources().getString(R.string.bayment)};

        adapter = new ARPayment(listPayment, this);

        init();
        binding.editPrice.addTextChangedListener(this);
        binding.btnAdd.setOnClickListener(this::onClickAdd);
        binding.btnDelete.setOnClickListener(this::onClickDeleteAll);
        binding.btnNext.setOnClickListener(v -> {
            dialogSecurity();
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
        binding.materialButton2.setOnClickListener(v -> {
            loadCalenderDialog();
        });
        argsReceipt = FragmentAddReceiptArgs.fromBundle(getArguments());
        if (argsReceipt != null) {
            binding.setTotalAll(argsReceipt.getReceiptToAddReceipt().getTotalAll());
        }
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
            binding.setDateDept(date);
        });
        return calendarView;
    }

    private void spinnerInit() {
        binding.spinnerTypeReceipt.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_style_add_receipt, typeReceipt);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTypeReceipt.setAdapter(adapter);
    }

    private void setReceipt() {
        Receipt model = argsReceipt.getReceiptToAddReceipt();
        model.setSubject(getReceipt().getSubject());
        model.setNote(getReceipt().getNote());
        model.setPayment(getPayment());
        model.setDateReceipt(handleTimeCount.getDate());
        vmReceipt.postReceipt(model).observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()) {
                if (model.getProducts() != null && model.getProducts().size() > 0)
                    vmItems.updatesQuantity(getIds(model.getProducts()), getItemQuantitys(model.getProducts()), getQuantitys(model.getProducts()))
                            .observe(getViewLifecycleOwner(), items -> {

                            });
                ActivityIntent.getInstance(requireContext()).userMakeChange(requireActivity());
            }
        });
    }

    private void dialogSecurity() {
        DialogSecurity dialogSecurity = new DialogSecurity(requireContext(), getLayoutInflater());
        if (!dialogSecurity.hasKey()){
            setReceipt();
            return;
        }
        if (!dialogSecurity.showDialog()) {
            setReceipt();
            return;
        }
        dialogSecurity.securityDialog(new ListenerSecurityDialog() {
            @Override
            public void onOk(Dialog dialog, boolean isTrue) {
                if (isTrue) {
                    setReceipt();
                    dialog.dismiss();
                    Log.d(TAG, "onOk: " + true);
                }
            }

            @Override
            public void onCancel(Dialog dialog) {
                dialog.dismiss();
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

    private void onClickAdd(View v) {
        double price = 0;
        try {
            price = Double.parseDouble(binding.editPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            price = 0;
        }
        Payment payment = new Payment();
        String date = binding.editDate.getText().toString().trim();
        if (date.isEmpty() || date.equals(getResources().getString(R.string.date)))
            date = handleTimeCount.getDate();
        payment.setDate(date);
        payment.setPrice(price);
        listPayment.add(payment);

        binding.setTotalAll(binding.getTotalAll() - payment.getPrice());
        adapter.notifyItemInserted(listPayment.size() - 1);
        binding.editPrice.setText("0");
    }
    private void onClickDeleteAll(View v) {
        if (listPayment.size() > 0) {
            adapter.notifyItemRangeRemoved(0, listPayment.size());
            listPayment.clear();
            binding.setTotalAll(argsReceipt.getReceiptToAddReceipt().getTotalAll());
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

    @Override
    public void onClick(Payment model) {

    }

    @Override
    public void onEdit(Payment model, int position) {

    }

    @Override
    public void onDelete(int position) {
        binding.setTotalAll(binding.getTotalAll() + listPayment.remove(position).getPrice());
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onPaid(CompoundButton buttonView, boolean isChecked, int position) {
        listPayment.get(position).setPaid(isChecked);
    }

    @Override
    public void onChangeDate(int position) {

    }

    @Override
    public void onChangePrice(int position) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        double total = binding.getTotalAll();
        if (s.length()>0){
            double price = Double.parseDouble(s.toString());
            if (price>total){
                binding.editPrice.setError(
                        getResources().getString(R.string.greater_than_total)
                        , ContextCompat.getDrawable(requireContext(),R.drawable.ic_twotone_warning_24)
                );
            }
        }
    }
}