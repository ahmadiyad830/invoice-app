package soft.mahmod.yourreceipt.view_fragment.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentReceiptBinding;
import soft.mahmod.yourreceipt.view_model.database.VMClient;
import soft.mahmod.yourreceipt.view_model.database.VMReceipt;
import soft.mahmod.yourreceipt.view_model.send.data.VMSendReceipt;


public class FragmentReceipt extends Fragment {
    private static final String TAG = "FragmentReceipt";
    private FragmentReceiptBinding binding;
    private VMSendReceipt vmSendReceipt;
    private VMReceipt vmReceipt;
    private VMClient vmClientl;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmReceipt = new ViewModelProvider(getViewModelStore(),new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        )).get(VMReceipt.class);
        vmClientl = new ViewModelProvider(getViewModelStore(),new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        )).get(VMClient.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vmSendReceipt = new ViewModelProvider(requireActivity()).get(VMSendReceipt.class);

        vmSendReceipt.getModel().observe(getViewLifecycleOwner(), receipt -> {
            binding.setModel(receipt);
        });
    }
}