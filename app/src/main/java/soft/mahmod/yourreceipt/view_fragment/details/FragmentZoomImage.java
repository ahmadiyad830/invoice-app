package soft.mahmod.yourreceipt.view_fragment.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentZoomImageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentZoomImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentZoomImage extends Fragment {
    private FragmentZoomImageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_zoom_image, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentZoomImageArgs uri = FragmentZoomImageArgs.fromBundle(getArguments());
        if (uri != null && !Boolean.parseBoolean(uri.getUrlToZoom())){
            binding.setUrl(uri.getUrlToZoom());
        }
    }
}