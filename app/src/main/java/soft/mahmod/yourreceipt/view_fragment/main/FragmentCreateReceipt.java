package soft.mahmod.yourreceipt.view_fragment.main;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateReceiptBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateReceipt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateReceipt extends Fragment {
    private FragmentCreateReceiptBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_receipt, container, false);

        return binding.getRoot();
    }
}