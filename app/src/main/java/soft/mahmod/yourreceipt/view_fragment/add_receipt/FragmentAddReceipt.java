package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.listeners.OnClickDeleteItemListener;
import soft.mahmod.yourreceipt.model.CreateReceipt;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_activity.MainActivity;
import soft.mahmod.yourreceipt.view_model.create_receipt.VMReceipt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddReceipt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddReceipt extends Fragment implements View.OnClickListener, OnClickDeleteItemListener<Products> {
    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    private HandleTimeCount handleTimeCount;
    private ARProducts adapter;
    private List<Products> listModel = new ArrayList<>();
    private boolean isSuccess;
    @SerializedName("num_loop")
    private int numLoop;
    private VMReceipt vmReceipt;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vmReceipt = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMReceipt.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_receipt, container, false);
        CreateReceipt model = new CreateReceipt();
        init();
        handleTimeCount.getDate();
        handleTimeCount.setTv_time(binding.txtTime);
        handleTimeCount.countDownStart();
        binding.setDate(handleTimeCount.getDate());

        binding.switchPrint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.setChecked(isChecked);
        });
        binding.txtVisibleRec.setOnClickListener(v -> {
            binding.setHasItem(!binding.getHasItem());
//            !binding.getHasItem()
        });
        binding.btnDown.setOnClickListener(v -> {

//            testProducts();
//            testReceipt();
            setReceipt();
        });
        binding.txtDeleteRec.setOnClickListener(v -> {
            deleteAllItem();
        });
        binding.setModel(model);
        return binding.getRoot();
    }

    private void setReceipt() {
        vmReceipt.setReceipt(getReceipt());
        vmReceipt.getErrorData().observe(
                getViewLifecycleOwner(),
                cash -> {
                    if (!cash.getError()) {
                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                }
        );
    }

    private Receipt getReceipt() {
        Receipt model = new Receipt();
        model.setSubject(binding.edtSubject.getText().toString().trim());
        model.setReceiptDate("time: " + binding.txtTime.getText().toString().trim() +" date: " + handleTimeCount.getDate());
        model.setTotalAll(binding.edtTotalAll.getText().toString().trim());
        model.setClientName(binding.edtClientName.getText().toString().trim());
        model.setClientPhone(binding.edtClientPhone.getText().toString().trim());
        binding.switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.setType(isChecked ? "take" : "dues");
        });
        return model;
    }

    private void init() {
        binding.setHasItem(true);
        handleTimeCount = new HandleTimeCount();
        adapter = new ARProducts(listModel, this);
        binding.itemsRec.setHasFixedSize(true);
        binding.itemsRec.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.itemsRec.setAdapter(adapter);
        binding.itemsRec.setVisibility(View.VISIBLE);
        loadProducts();
    }

    public void loadProducts() {
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.addClient.setOnClickListener(this);
        binding.addItem.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        handleTimeCount.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        get
//        Gson gson = new Gson();
//        String json = mPrefs.getString("SerializableObject", "");
//        CreateReceipt saveModel = gson.fromJson(json, CreateReceipt.class);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //        set
//        SharedPreferences mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor prefsEditor = mPrefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(YourSerializableObject);
//        prefsEditor.putString("SerializableObject", json);
//        prefsEditor.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (binding.addClient.getId() == id) {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentAddReceipt_to_fragmentAddClient);
        } else if (binding.addItem.getId() == id) {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentAddReceipt_to_fragmentAddItem);
        } else if (binding.btnBack.getId() == id) {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    @Override
    public void onClickDeleteItem(Products model, int position) {
    }

    @Override
    public void onClickItem(Products model) {

    }

    private void deleteAllItem() {
    }

    private void totalAll() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}