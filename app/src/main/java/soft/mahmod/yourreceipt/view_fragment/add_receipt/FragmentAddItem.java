package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARItems;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentAddItemBinding;
import soft.mahmod.yourreceipt.listeners.OnClickItemListener;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.view_model.VMItemByEmail;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddItem extends Fragment implements OnClickItemListener<Items>  {
    private static final String TAG = "FragmentAddItem";
    private FragmentAddItemBinding binding;
    private VMItemByEmail vmItemByEmail;
    private ARItems adapter;
    private SessionManager manager;
    private List<Items> modelList = new ArrayList<>();
    private NavController controller;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = SessionManager.getInstance(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false);
        init();

        binding.swipeList.setOnRefreshListener(() -> {
            modelList.clear();
            adapter.notifyDataSetChanged();
            loadItems();

        });
        loadItems();
        return binding.getRoot();
    }

    private void init() {
        adapter = new ARItems(modelList, this);
        binding.recItem.setHasFixedSize(true);
        binding.recItem.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recItem.setAdapter(adapter);
        vmItemByEmail = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        )).get(VMItemByEmail.class);
    }

    private void loadItems() {
        vmItemByEmail.itemByEmail(manager.getUser().getEmail())
                .observe(getViewLifecycleOwner(), items -> {
                    if (items != null) {
                        int oldSize = modelList.size();
                        modelList.addAll(items);
                        adapter.notifyItemRangeInserted(oldSize, modelList.size());
                    }
                    binding.swipeList.setRefreshing(false);

                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        binding.fabToCreateItem.setOnClickListener(v -> {

            controller.navigate(R.id.action_fragmentAddItem_to_fragmentCreateItem);
        });
        binding.btnSearch.setOnClickListener(v -> {
            binding.setIsSearch(true);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        int size = modelList.size();
        modelList.clear();
        adapter.notifyItemRangeRemoved(size, modelList.size());
    }

    @Override
    public void onClickItem(Items model) {
        FragmentAddItemDirections.ActionFragmentAddItemToFragmentCreateProducts
                argsCreateProducts = FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateProducts();
        argsCreateProducts.setItemArgs(model);
        controller.navigate(argsCreateProducts);
    }
}