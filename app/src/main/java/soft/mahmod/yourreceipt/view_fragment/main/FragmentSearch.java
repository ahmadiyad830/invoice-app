package soft.mahmod.yourreceipt.view_fragment.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARReceipt;
import soft.mahmod.yourreceipt.databinding.FragmentSearchBinding;
import soft.mahmod.yourreceipt.listeners.OnReceiptItemClick;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.view_activity.ActivityDetails;
import soft.mahmod.yourreceipt.view_model.VMReceiptByClientName;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment implements OnReceiptItemClick {
    private FragmentSearchBinding binding;
    private ARReceipt adapter;
    private List<Receipt> listModel = new ArrayList<>();
    private VMReceiptByClientName vmReceiptByClientName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        vmReceiptByClientName = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        )).get(VMReceiptByClientName.class);
        init();
        binding.btnSearch.setOnClickListener(v -> {
            binding.setIsSearch(true);
            String client = binding.textSearch.getText().toString().trim();
            listModel.clear();
            adapter.notifyDataSetChanged();
            if (!client.isEmpty()){
                loadReceipt();
            }
        });
        return binding.getRoot();
    }

    private void loadReceipt() {
        vmReceiptByClientName.receiptByClientName(binding.textSearch.getText().toString().trim())
                .observe(getViewLifecycleOwner(), receipts -> {
                    if (receipts != null) {
                        int oldSize = listModel.size();
                        listModel.addAll(receipts);
                        adapter.notifyItemRangeInserted(oldSize, listModel.size());
                    }
                });
    }

    private void init() {
        adapter = new ARReceipt(listModel, this);
        binding.searchRecycler.setHasFixedSize(true);
        binding.searchRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.searchRecycler.setAdapter(adapter);
    }

    @Override
    public void itemClick(Receipt model) {
        Intent intent = new Intent(requireContext(), ActivityDetails.class);
        intent.putExtra("model",model);
        startActivity(intent);
    }
}