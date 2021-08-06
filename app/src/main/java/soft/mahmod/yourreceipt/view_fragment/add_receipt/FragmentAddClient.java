package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentAddClientBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddClient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddClient extends Fragment {
    private FragmentAddClientBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_client, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fabToCreateClient.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_fragmentAddClient_to_fragmentCreateClient);
        });
    }
}