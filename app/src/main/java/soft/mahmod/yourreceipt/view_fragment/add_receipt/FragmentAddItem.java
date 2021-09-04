package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARProducts;
import soft.mahmod.yourreceipt.adapter.firebase.ARItems;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentAddItemBinding;
import soft.mahmod.yourreceipt.databinding.LayoutItemsBinding;
import soft.mahmod.yourreceipt.listeners.OnClickItemListener;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddItem extends Fragment implements OnClickItemListener<Items>  {
    private static final String TAG = "FragmentAddItem";
    private FragmentAddItemBinding binding;
    private ARItems arItems;
    private List<Items> modelList = new ArrayList<>();
    private NavController controller;

    private HandleTimeCount handleTimeCount;
    private final List<Products> listProduct = new ArrayList<>();
    private int sizeProduct = 0;
    private ARProducts arProducts;
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false);
        arItems = new ARItems(options, this);

        handleTimeCount = new HandleTimeCount();
        arProducts = new ARProducts(listProduct, this);


        handleTimeCount.setTv_time(binding.txtTime);
        handleTimeCount.countDownStart();
        binding.setDate(handleTimeCount.getDate());


        init();
        binding.fabToCreateItem.setOnClickListener(v -> {
            loadItems();
        });
        loadItems();
        return binding.getRoot();
    }
    private void init() {
        binding.recItem.setHasFixedSize(true);
        binding.recItem.setAdapter(arProducts);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        binding.fabToCreateItem.setOnClickListener(v -> {

//            controller.navigate(R.id.action_fragmentAddItem_to_fragmentCreateItem);
        });
//        binding.btnSearch.setOnClickListener(v -> {
//            binding.setIsSearch(true);
//        });
    }

    @Override
    public void onClickItem(Items model) {
//        FragmentAddItemDirections.ActionFragmentAddItemToFragmentCreateProducts
//                argsCreateProducts = FragmentAddItemDirections.actionFragmentAddItemToFragmentCreateProducts();
//        argsCreateProducts.setItemArgs(model);
//        controller.navigate(argsCreateProducts);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        getViewModelStore().clear();
    }
    private void loadItems() {
        BottomSheetDialog itemBottomDialog = new BottomSheetDialog(requireContext());
        LayoutItemsBinding itemsBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),
                R.layout.layout_items, getView().findViewById(R.id.container_items), false);
        itemBottomDialog.setContentView(itemsBinding.getRoot());
        itemsBinding.recyclerItemsView.setAdapter(arItems);
        itemsBinding.imageClose.setOnClickListener(v1 -> {
            arItems.stopListening();
            itemBottomDialog.dismiss();
        });
        FrameLayout frameLayout = itemBottomDialog.findViewById(
                com.google.android.material.R.id.design_bottom_sheet);
        if (frameLayout == null) {
            BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        itemBottomDialog.show();
        if (itemBottomDialog.isShowing()) {
            arItems.startListening();
        } else {
            arItems.stopListening();
        }
    }
}