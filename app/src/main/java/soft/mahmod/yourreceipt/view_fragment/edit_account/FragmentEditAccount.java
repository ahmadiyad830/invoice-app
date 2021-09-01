package soft.mahmod.yourreceipt.view_fragment.edit_account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ActivityIntent;
import soft.mahmod.yourreceipt.databinding.FragmentEditAccountBinding;
import soft.mahmod.yourreceipt.model.Store;
import soft.mahmod.yourreceipt.utils.IntentActivity;
import soft.mahmod.yourreceipt.view_model.database.VMStore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditAccount extends Fragment {
    private static final String TAG = "FragmentEditAccount";
    private FragmentEditAccountBinding binding;
    private VMStore vmStore;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_account, container, false);
        loadStore();
        binding.btnDown.setOnClickListener(v -> dialog());
        binding.btnBack.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentEditAccount_to_menu_setting);
        });
        binding.btnLogo.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, IntentActivity.REQUEST_CAMERA);
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentActivity.REQUEST_CAMERA && resultCode==Activity.RESULT_OK && data!=null){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Log.d(TAG, "onActivityResult: "+thumbnail.toString());
            binding.logo.setImageBitmap(thumbnail);
        }
    }

    private void loadStore() {
        vmStore.getStore().observe(getViewLifecycleOwner(), store -> {
            binding.setModel(store);
        });
    }

    private void dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Edit Account");
        dialog.setMessage("are you sure");
        dialog.setPositiveButton("OK", (dialog1, which) -> {
            createStore();
            dialog1.dismiss();
            ActivityIntent.getInstance(requireContext()).userMakeChange(requireActivity());
            Toast.makeText(requireContext(), "Your store has been created", Toast.LENGTH_SHORT).show();
        });
        dialog.setNegativeButton("NO", (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.create();
        dialog.show();
    }

    private void createStore() {

    }

    private Store getStore() {
        Store store = new Store();
        String name = binding.edtName.getText().toString().trim();
        store.setName(name);
        String number = binding.edtPhoneNum.getText().toString().trim();
        store.setNumberPhone(Integer.parseInt(number));
        String email = binding.edtEmail.getText().toString().trim();
        store.setEmail(email);
        String address1 = binding.edtAddress1.getText().toString().trim();
        store.setAddress1(address1);
        String address2 = binding.edtAddress2.getText().toString().trim();
        store.setAddress2(address2);
        try {
            store.setLogo(getPath());
        } catch (Exception e) {
            store.setLogo("");
            e.printStackTrace();
        }
        return store;
    }


    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}