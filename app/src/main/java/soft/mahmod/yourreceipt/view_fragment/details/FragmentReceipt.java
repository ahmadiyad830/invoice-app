package soft.mahmod.yourreceipt.view_fragment.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentReceiptBinding;
import soft.mahmod.yourreceipt.view_model.ui.VMSendReceipt;


public class FragmentReceipt extends Fragment {
    private static final String TAG = "FragmentReceipt";
    private FragmentReceiptBinding binding;
    private VMSendReceipt vmSendReceipt;

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
        String asd = "https://scontent.famm10-1.fna.fbcdn.net/v/t1.6435-9/" +
                "56414051_860312110987231_48404707658" +
                "47445504_n.jpg?_nc_cat=101&ccb=1-3&_nc_sid" +
                "=09cbfe&_nc_eui2=AeHY4AvCwMY29M2Wtnp5V4au2rs3220TwRz" +
                "auzfbbRPBHOtS54pbDmfFHLJrK5NII3P4ykk8s8Z6vGLmu_uRUCDN&_n" +
                "c_ohc=kC70psJ2QD4AX-kW4Xj&_nc_ht=scontent.famm10-1.fna&oh=" +
                "a9e231765d83d09ece1c307ff72ad429&oe=612B0D2B";
        binding.setImageUrl(asd);

    }
}