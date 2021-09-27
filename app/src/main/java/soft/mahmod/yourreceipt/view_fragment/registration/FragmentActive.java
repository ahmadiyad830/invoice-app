package soft.mahmod.yourreceipt.view_fragment.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_active, container, false);
        binding.btnWhtsapp.setOnClickListener(v -> {
            Uri uri = Uri.parse("smsto:" + getResources().getString(R.string.my_phone_num));
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, ""));
        });
        binding.btnPhoneCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getResources().getString(R.string.my_phone_num)));
            startActivity(intent);
        });
        return binding.getRoot();
    }
}