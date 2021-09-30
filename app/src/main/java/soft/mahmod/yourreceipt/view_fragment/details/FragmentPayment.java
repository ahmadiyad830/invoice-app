package soft.mahmod.yourreceipt.view_fragment.details;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARFirebasePayment;
import soft.mahmod.yourreceipt.databinding.FragmentEditPaymentBinding;
import soft.mahmod.yourreceipt.databinding.FragmentPaymentBinding;
import soft.mahmod.yourreceipt.helper.DialogWithView;
import soft.mahmod.yourreceipt.helper.SimpleDialog;
import soft.mahmod.yourreceipt.listeners.ListenerFirebasePayment;
import soft.mahmod.yourreceipt.listeners.SimpleDialogListener;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPayment extends Fragment implements DatabaseUrl, OnFailureListener, ListenerFirebasePayment {
    private static final String TAG = "FragmentPayment";
    private FragmentPaymentBinding binding;
    private VMSendData vmSendData;
    private ARFirebasePayment arFirebasePayment;
    private FirebaseRecyclerOptions<Payment> options;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vmSendData = new ViewModelProvider(requireActivity()).get(VMSendData.class);
        binding.recPayment.setHasFixedSize(true);


        vmSendData.getReceipt().observe(getViewLifecycleOwner(), receipt -> {
            if (receipt != null && receipt.getPayment() != null && receipt.getPayment().getListPayment() != null) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                String uid = FirebaseAuth.getInstance().getUid();
                Query query = reference.child(RECEIPT)
                        .child(uid)
                        .child(receipt.getReceiptId())
                        .child(PAYMENT)
                        .child(LIST_PAYMENT);
                options = new FirebaseRecyclerOptions.Builder<Payment>()
                        .setQuery(query, Payment.class).build();
                arFirebasePayment = new ARFirebasePayment(options, this);

                binding.recPayment.setAdapter(arFirebasePayment);
                arFirebasePayment.startListening();

            }
        });

    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit(Payment model, int position) {
        FragmentEditPaymentBinding binding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.fragment_edit_payment, null, false
        );
        binding.setModel(model);
        Dialog dialog = DialogWithView.dialogWthView(requireContext(), binding.getRoot(), true);
        binding.btnClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        binding.btnDown.setOnClickListener(v -> {
            arFirebasePayment.getRef(position)
                    .setValue(getPayment(model, binding));
            dialog.dismiss();
        });
        binding.btnDelete.setOnClickListener(v -> {
            arFirebasePayment.getRef(position)
                    .removeValue();
            dialog.dismiss();
        });
        binding.editDate.setOnClickListener(v -> {
            AlertDialog dialogCalendur = SimpleDialog.simpleDialogWihtView(
                    dialog.getContext()
                    , createCalendar(dialog.getContext(), binding.editDate)
                    , DialogInterface::dismiss
            );
            dialogCalendur.show();
        });
        dialog.show();
    }
    private CalendarView createCalendar(Context context, TextView editText) {
        CalendarView calendarView = new CalendarView(context);
        calendarView.setMinDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "/" + month + "/" + year;
            editText.setText(date);
        });
        return calendarView;
    }
    private Payment getPayment(Payment model, FragmentEditPaymentBinding binding) {
        Payment payment = new Payment();
        String date = binding.editDate.getText().toString().trim();
        double price;
        try {
            price = Double.parseDouble(binding.editPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            price = 0;
        }
        payment.setDate(date);
        payment.setPrice(price);
        payment.setPaid(binding.switchPaid.isChecked());
        return payment;
    }
}