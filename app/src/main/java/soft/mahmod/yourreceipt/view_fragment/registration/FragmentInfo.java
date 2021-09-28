package soft.mahmod.yourreceipt.view_fragment.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentInfoBinding;
import soft.mahmod.yourreceipt.model.Store;
import soft.mahmod.yourreceipt.view_model.database.VMStore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInfo extends Fragment {
    private VMStore vmStore;
    private static final String TAG = "FragmentInfo456";
    private FragmentInfoBinding binding;
    private NavController controller;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmStore = new ViewModelProvider
                (getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMStore.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        FragmentInfoArgs argsEmail = FragmentInfoArgs.fromBundle(getArguments());
        FragmentInfoDirections.ActionFragmentInfoToFragmentSignIn passEmail =
                FragmentInfoDirections.actionFragmentInfoToFragmentSignIn();
        binding.btnSignup.setOnClickListener(v -> {
            binding.setProgress(true);
            Store store = new Store();
            String storeName = binding.storeName.getText().toString();
            store.setName(storeName);
            int phoneNum;
            try {
                phoneNum = Integer.parseInt(binding.phoneNum.getText().toString());
            } catch (NumberFormatException e) {
                phoneNum = 0;
            }
            store.setPhone(phoneNum);
            String storeAddress = binding.storeAddress.getText().toString();
            store.setAddress1(storeAddress);
            String numberSecuirty = binding.storeAddress.getText().toString();
            store.setSecurity(numberSecuirty);
            vmStore.postStore(store).observe(getViewLifecycleOwner(), store1 -> {
                if (!store.getError()) {
                    passEmail.setArgsEmail(argsEmail.getArgsEmail());
                    controller.navigate(passEmail);
                }
                binding.setProgress(false);
            });
        });
        binding.btnSkip.setOnClickListener(v -> {
            controller.navigate(FragmentInfoDirections.actionFragmentInfoToFragmentSignIn());
        });

    }
}