package soft.mahmod.yourreceipt.view_fragment.details;

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

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARPayment;
import soft.mahmod.yourreceipt.databinding.FragmentPaymentBinding;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.view_model.database.VMPayment;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendReceipt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPayment extends Fragment implements ARPayment.ListenerOnClick {
    private static final String TAG = "FragmentPayment";
    private FragmentPaymentBinding binding;
    private VMSendReceipt vmSendReceipt;
    private ARPayment adapter;
    private VMPayment vmPayment;
    private Receipt receipt;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmPayment = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMPayment.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_payment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vmSendReceipt = new ViewModelProvider(requireActivity()).get(VMSendReceipt.class);
        binding.recPayment.setHasFixedSize(true);
        vmSendReceipt.getModel().observe(getViewLifecycleOwner(),receipt -> {
            if (receipt != null) {
                this.receipt = receipt;
            }
            adapter = new ARPayment(receipt.getPayment().getListPayment(), this);
            adapter.setIsCreate(View.GONE);
            binding.recPayment.setAdapter(adapter);
        });

    }

    @Override
    public void payment(Payment model) {

    }

    @Override
    public void deletePayment(int position) {

    }

    @Override
    public void paid( boolean isChecked, int position) {
        vmPayment.putPaid(receipt.getReceiptId(),position,isChecked)
                .observe(getViewLifecycleOwner(),payment -> {
                    Log.d(TAG, "paid: "+payment.toString());
                });
    }
}