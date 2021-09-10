package soft.mahmod.yourreceipt.view_fragment.create;

import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentCreateItemBinding;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.utils.DialogConfirm;
import soft.mahmod.yourreceipt.utils.DialogListener;
import soft.mahmod.yourreceipt.view_model.database.VMItems;

public class MainCreateItem extends Fragment {

    private static final String TAG = "FragmentCreateItem";
    private FragmentCreateItemBinding binding;
    private VMItems vmItems;
    private NavController controller;
    private final List<String> listWarning = new ArrayList<>();
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmItems = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(
                        requireActivity().getApplication()
                )
        ).get(VMItems.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_item, container, false);


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        MainCreateItemArgs itemArgs = MainCreateItemArgs.fromBundle(getArguments());
        binding.setModel(itemArgs.getMainItemToCreateItem());
        binding.btnDown.setOnClickListener(v -> {
            if (warning()){
                dialogWarning();
            }else postItem();
        });
    }

    private void postItem() {
        vmItems.postItem(getModel())
                .observe(getViewLifecycleOwner(), cash -> {
                    if (!cash.getError()) {
                        requireActivity().onBackPressed();
//                        controller.navigate(FragmentCreateClientDirections.actionFragmentCreateClientToFragmentAddClient());
                    }
                });
    }

    private Items getModel() {
        double itemPrice = 0;
        try {
            itemPrice = Double.parseDouble(binding.edtPrice.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        double quantity = 0;
        try {
            quantity = Double.parseDouble(binding.edtQuantity.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        double itemTax = 0;
        try {
            itemTax = Double.parseDouble(binding.edtTax.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        double discount = 0;
        try {
            discount = Double.parseDouble(binding.edtDiscount.getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String itemName = binding.edtName.getText().toString().trim();
        String itemNote = binding.edtSubject.getText().toString().trim();
        return new Items(itemName, itemPrice, discount, itemTax, itemNote, quantity);
    }

    private boolean warning() {
        if (getModel().getName().equals("")) {
            listWarning.add(getResources().getString(R.string.name));
        }
        if (getModel().getPrice() == 0.0) {
            listWarning.add(getResources().getString(R.string.price));
        }
        if (getModel().getQuantity() == 0.0) {
            listWarning.add(getResources().getString(R.string.quantity));
        }
        return listWarning.size() > 0;
    }
    private void dialogWarning() {
        DialogConfirm dialogConfirm = new DialogConfirm(requireContext());
        dialogConfirm.setDialogListener(new DialogListener() {
            @Override
            public void clickOk(DialogInterface dialog) {
                listWarning.clear();
                postItem();
            }

            @Override
            public void clickCancel(DialogInterface dialog) {
                dialog.dismiss();
                listWarning.clear();
            }
        });
        dialogConfirm.setIcon(R.drawable.ic_twotone_warning_24);
        dialogConfirm.listenerDialog();
//        TODO translate
        StringBuilder warning = new StringBuilder("\n");
        for (int i = 0; i < listWarning.size(); i++) {
            warning.append(listWarning.get(i)).append("\n");
        }
        dialogConfirm.createDialog(getResources().getString(R.string.warning),
                getResources().getString(R.string.warning_not_add)
                        + warning.toString()
        );
        dialogConfirm.showDialog();
    }
}
