package soft.mahmod.yourreceipt.view_fragment.edit_account;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentEditAccountBinding;
import soft.mahmod.yourreceipt.view_activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditAccount extends Fragment {
    private static final String TAG = "FragmentEditAccount";
    private FragmentEditAccountBinding binding;
    private SessionManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_account, container, false);
        init();
        binding.btnDown.setOnClickListener(v -> dialog());
        binding.btnBack.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentEditAccount_to_menu_setting);
        });
        return binding.getRoot();
    }

    private void init() {
        manager = SessionManager.getInstance(requireContext());
    }

    private void changePassword() {

    }

    private void dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Edit Account");
        dialog.setMessage("are you sure");
        dialog.setPositiveButton("OK", (dialog1, which) -> {
            changePassword();
            dialog1.dismiss();
        });
        dialog.setNegativeButton("NO", (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.create();
        dialog.show();
    }
}