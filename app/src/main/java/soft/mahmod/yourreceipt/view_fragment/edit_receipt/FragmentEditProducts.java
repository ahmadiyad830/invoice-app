package soft.mahmod.yourreceipt.view_fragment.edit_receipt;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentEditProductsBinding;

public class FragmentEditProducts extends DialogFragment {
    private FragmentEditProductsBinding binding;
    private Dialog dialog;
    private ViewGroup container;

    public FragmentEditProducts(ViewGroup container) {
        this.container = container;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        dialog = new Dialog(requireContext());
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.fragment_edit_products, this.container, false);
        getDialog().setContentView(binding.getRoot());
        return binding.getRoot();
    }
}