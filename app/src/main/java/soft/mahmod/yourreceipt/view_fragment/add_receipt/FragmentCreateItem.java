package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentCreateItemBinding;
import soft.mahmod.yourreceipt.model.Items;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateItem extends Fragment {
    private static final String TAG = "FragmentCreateItem";
    private FragmentCreateItemBinding binding;
    private SessionManager manager ;
    private NavController controller;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = SessionManager.getInstance(requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_item, container, false);

        binding.btnDown.setOnClickListener(v -> {
            createItem();
            Log.d(TAG, "onCreateView: click");
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        binding.btnBack.setOnClickListener(v -> {
            controller.navigate(R.id.action_fragmentCreateItem_to_fragmentAddItem);
        });
    }

    private void createItem() {
    }
    private Items getModel(){
        String userId = "model.getUserId()";
        String itemName = binding.edtName.getText().toString().trim();
        String itemPrice= binding.edtPrice.getText().toString().trim();
        String quantity= binding.edtQuantity.getText().toString().trim();
        String itemTax= binding.edtTax.getText().toString().trim();
        String itemNote= binding.edtSubject.getText().toString().trim();
        return new Items(userId,itemName,itemPrice,itemTax,itemNote,quantity);
    }

}