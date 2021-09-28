package soft.mahmod.yourreceipt.view_fragment.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARFirebaseAdapterPayment;
import soft.mahmod.yourreceipt.databinding.FragmentPaymentBinding;
import soft.mahmod.yourreceipt.listeners.ListenerPayment;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendReceipt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPayment extends Fragment implements ListenerPayment, DatabaseUrl, OnFailureListener {
    private static final String TAG = "FragmentPayment";
    private FragmentPaymentBinding binding;
    private VMSendReceipt vmSendReceipt;
    private ARFirebaseAdapterPayment arFirebaseAdapterPayment;
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
        vmSendReceipt = new ViewModelProvider(requireActivity()).get(VMSendReceipt.class);
        binding.recPayment.setHasFixedSize(true);


        vmSendReceipt.getModel().observe(getViewLifecycleOwner(), receipt -> {
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
                arFirebaseAdapterPayment = new ARFirebaseAdapterPayment(options, this);

                binding.recPayment.setAdapter(arFirebaseAdapterPayment);
                arFirebaseAdapterPayment.startListening();

            }
        });

    }

    @Override
    public void onClick(Payment model) {

    }

    @Override
    public void onEdit(Payment model, int position) {

    }

    @Override
    public void onDelete(int position) {
        arFirebaseAdapterPayment
                .getRef(position)
                .removeValue();

    }


    @Override
    public void onPaid(CompoundButton buttonView, boolean isChecked, int position) {
        arFirebaseAdapterPayment.getRef(position)
                .child(PAYMENT)
                .child(LIST_PAYMENT)
                .child(String.valueOf(position))
                .child(PAID)
                .setValue(isChecked)
                .addOnFailureListener(this);

    }

    @Override
    public void onChangeDate(int position) {

    }

    @Override
    public void onChangePrice(int position) {

    }


    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}