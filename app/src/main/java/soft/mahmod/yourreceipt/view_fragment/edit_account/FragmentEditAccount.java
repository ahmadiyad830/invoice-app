package soft.mahmod.yourreceipt.view_fragment.edit_account;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentEditAccountBinding;
import soft.mahmod.yourreceipt.model.Store;
import soft.mahmod.yourreceipt.view_model.database.VMStore;
import soft.mahmod.yourreceipt.view_model.storage.VMLogo;

public class FragmentEditAccount extends Fragment {
    private static final String TAG = "FragmentEditAccount";
    private FragmentEditAccountBinding binding;
    private VMStore vmStore;
    private VMLogo vmLogo;
    private Intent intent;
    public final static int IMAGE_REQUEST = 200;
    private Uri uri;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmStore = new ViewModelProvider
                (getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMStore.class);

        vmLogo = new ViewModelProvider
                (getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMLogo.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_account, container, false);
        loadStore();
        binding.btnDown.setOnClickListener(v -> dialog());
        binding.btnBack.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentEditAccount_to_menu_setting);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLogo.setOnClickListener(v -> {
            openGallery();
        });
    }


    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            binding.setLogo(uri.toString());
        }
    }

    private void dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Edit Account");
        dialog.setMessage("are you sure");
        dialog.setPositiveButton("OK", (dialog1, which) -> {
            createStore();
            dialog1.dismiss();
//            ActivityIntent.getInstance(requireContext()).userMakeChange(requireActivity());
            Toast.makeText(requireContext(), "Your store has been created", Toast.LENGTH_SHORT).show();
        });
        dialog.setNegativeButton("NO", (dialog1, which) -> dialog1.dismiss());
        dialog.create();
        dialog.show();
    }

    private void createStore() {
        vmLogo.postLogo(uri).observe(getViewLifecycleOwner(), cash -> {
            if (!cash.getError()) {
                Log.d(TAG, "createStore:getMessage" + cash.getMessage());
                vmStore.postStore(getStore(cash.getMessage())).observe(getViewLifecycleOwner(), cash1 -> {
                    if (cash1.getError())
                        binding.setError(cash1.getMessage());
                });
            } else binding.setError(cash.getMessage());
        });
    }

    private Store getStore(String url) {
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
        store.setLogo(url);
        return store;
    }


    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private void loadStore() {
        vmStore.getStore().observe(getViewLifecycleOwner(), store -> {
            if (!store.getError()){
                binding.setModel(store);
                try {
                    binding.setLogo(store.getLogo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "loadStore: "+store.getMessage());
        });
    }


}