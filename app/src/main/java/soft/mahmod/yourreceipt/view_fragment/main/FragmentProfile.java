package soft.mahmod.yourreceipt.view_fragment.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.view_model.VMAddLogo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {
    private static final String TAG = "FragmentProfile";
    private VMAddLogo vmAddLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String path = "https://www.ford.com/is/image/content/dam/vdm_ford/live/en_us/ford/nameplate/mustang/2021/collections/dm/20_FRD_MST_40829.tif?croppathe=1_3x2&wid=900";
        vmAddLogo = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(VMAddLogo.class);
        SessionManager manager = SessionManager.getInstance(requireContext());
        vmAddLogo.addLogo(path, manager.getUser().getEmail()).observe(getViewLifecycleOwner(), cash -> {
            if (cash != null) {
                Log.d(TAG, "onViewCreated: " + cash.toString());
            }
        });
    }
}