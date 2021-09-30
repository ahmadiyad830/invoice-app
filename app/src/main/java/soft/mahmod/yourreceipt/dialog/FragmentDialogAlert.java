package soft.mahmod.yourreceipt.dialog;

import android.app.AlertDialog;
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

public class FragmentDialogAlert extends DialogFragment {
    private FragmentEditProductsBinding binding;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.title))
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {} );
        return builder.create();

//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//        builder.setView(inflater.inflate(getRecLayout(),null,false));
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_products,container,false);
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_products, container, false);

        return view;

    }

}
