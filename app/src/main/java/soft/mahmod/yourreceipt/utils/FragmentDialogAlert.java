package soft.mahmod.yourreceipt.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FragmentDialogAlert extends DialogFragment {
    private int recLayout;

    public int getRecLayout() {
        return recLayout;
    }

    public void setRecLayout(int recLayout) {
        this.recLayout = recLayout;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(getRecLayout(),null,false));
        return super.onCreateDialog(savedInstanceState);
    }
}
