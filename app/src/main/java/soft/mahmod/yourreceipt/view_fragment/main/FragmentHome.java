package soft.mahmod.yourreceipt.view_fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARReceipt;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.ActivityMainBinding;
import soft.mahmod.yourreceipt.databinding.FragmentHomeBinding;
import soft.mahmod.yourreceipt.listeners.OnReceiptItemClick;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.view_activity.ActivityDetails;
import soft.mahmod.yourreceipt.view_model.VMCreateReceipt;
import soft.mahmod.yourreceipt.view_model.VMReceiptByEmail;


public class FragmentHome extends Fragment implements OnReceiptItemClick {
    private static final String TAG = "FragmentHome";
    private FragmentHomeBinding binding;
    private VMCreateReceipt vmCreateReceipt;
    private VMReceiptByEmail vmReceiptByEmail;
    private List<Receipt> listModel = new ArrayList<>();
    private ARReceipt adapter;
    private SessionManager manager;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        init();

        loadReceipt();


        binding.swipeList.setOnRefreshListener(() -> {
            listModel.clear();
            adapter.notifyDataSetChanged();
            loadReceipt();
        });
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadReceipt() {
        vmReceiptByEmail.receiptByEmail(manager.getUser().getEmail())
                .observe(getViewLifecycleOwner(), receipts -> {
                    if (receipts!=null){
                        listModel.addAll(receipts);
                        adapter.notifyDataSetChanged();
                    }
                    binding.swipeList.setRefreshing(false);
                });
    }

    private void init() {
        vmReceiptByEmail = new ViewModelProvider
                (this, new ViewModelProvider.AndroidViewModelFactory
                (requireActivity().getApplication()))
                .get(VMReceiptByEmail.class);
        manager = SessionManager.getInstance(requireContext());
        adapter = new ARReceipt(listModel,this);
        binding.mainRecycler.setHasFixedSize(true);
        binding.mainRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.mainRecycler.setAdapter(adapter);
    }

    @Override
    public void itemClick(Receipt model) {
        Intent intent = new Intent(requireContext(), ActivityDetails.class);
        intent.putExtra("model",model);
        startActivity(intent);
    }
}