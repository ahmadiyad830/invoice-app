package soft.mahmod.yourreceipt.view_fragment.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentActiveBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentActive#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentActive extends Fragment {
    private FragmentActiveBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_active, container, false);
        binding.materialButton.setOnClickListener(v -> {
            Uri uri = Uri.parse("smsto:" + "0782317354");
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, ""));
        });
        return binding.getRoot();
    }
}