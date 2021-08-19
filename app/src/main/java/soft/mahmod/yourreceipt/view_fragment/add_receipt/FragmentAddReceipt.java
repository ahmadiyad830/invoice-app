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


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.model.CreateReceipt;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_activity.MainActivity;
import soft.mahmod.yourreceipt.view_model.VMCreateReceipt;
import soft.mahmod.yourreceipt.view_model.room_products.VMProducts;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddReceipt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddReceipt extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    private HandleTimeCount handleTimeCount;
    private ARProducts adapter;
    private List<Products> listModel = new ArrayList<>();
    private VMProducts vmProducts;

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
            testReceipt();
        });
        binding.setModel(model);
        return binding.getRoot();
    }

    private void testReceipt() {
        Receipt model = new Receipt(SessionManager.getInstance(requireContext()).getUser().getUserId());
        model.setSubject(binding.edtSubject.getText().toString().trim());
        model.setReceiptDate("time: " + binding.txtTime.getText().toString().trim() + "\n"
                + "date: " + handleTimeCount.getDate());
        model.setTotalAll(binding.edtTotalAll.getText().toString().trim());
        model.setClientName(binding.edtClientName.getText().toString().trim());
        model.setClientPhone(binding.edtClientPhone.getText().toString().trim());

        VMCreateReceipt vmCreateReceipt = new ViewModelProvider(requireActivity()
                , new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(VMCreateReceipt.class);
        vmCreateReceipt.createReceipt(model).observe(getViewLifecycleOwner(), cash -> {
            Log.d(TAG, "testReceipt: " + cash);
        });
    }

    private void init() {
        binding.setHasItem(true);
        vmProducts = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMProducts.class);
        handleTimeCount = new HandleTimeCount();
        adapter = new ARProducts(listModel);
        binding.itemsRec.setHasFixedSize(true);
        binding.itemsRec.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.itemsRec.setAdapter(adapter);
        binding.itemsRec.setVisibility(View.VISIBLE);
        loadProducts();
    }

    public void loadProducts() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(vmProducts.getProducts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(products -> {
                    int old = listModel.size();
                    listModel.addAll(products);
                    adapter.notifyDataSetChanged();
                    binding.setHasItem(true);
                }));
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
}