package soft.mahmod.yourreceipt.view_fragment.main;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentDetailsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetails extends Fragment {
    private FragmentDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details, container, false);
        FragmentDetailsArgs args = FragmentDetailsArgs.fromBundle(getArguments());
        binding.setModel(args.getArgsDetails());
        return binding.getRoot();
    }
}