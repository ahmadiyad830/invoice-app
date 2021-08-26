package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Repo;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARClients;
import soft.mahmod.yourreceipt.adapter.ARItems;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.databinding.LayoutClientBinding;
import soft.mahmod.yourreceipt.databinding.LayoutItemsBinding;
import soft.mahmod.yourreceipt.listeners.OnClickItemListener;
import soft.mahmod.yourreceipt.listeners.OnClientClick;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.CreateReceipt;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_activity.MainActivity;
import soft.mahmod.yourreceipt.view_model.create_receipt.VMReceipt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddReceipt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddReceipt extends Fragment implements View.OnClickListener
        , OnClickItemListener<Items>, OnClientClick, DatabaseUrl {
    private static final String TAG = "FragmentAddReceipt";
    private FragmentAddReceiptBinding binding;
    private HandleTimeCount handleTimeCount;
    private VMReceipt vmReceipt;
    private ARItems adapter;
    private ARClients adapterClient;
    private DatabaseReference reference;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference();
        vmReceipt = new ViewModelProvider(
                getViewModelStore(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(VMReceipt.class);
        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reference.child(ITEMS + FirebaseAuth.getInstance().getUid()), Items.class)
                .build();
        FirebaseRecyclerOptions<Client> optionsClient = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(reference.child(CLIENT + FirebaseAuth.getInstance().getUid()), Client.class)
                .build();
        adapter = new ARItems(options, this);

        adapterClient = new ARClients(optionsClient, this);
        handleTimeCount = new HandleTimeCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_receipt, container, false);
        CreateReceipt model = new CreateReceipt();

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
        model.setReceiptDate("time: " + binding.txtTime.getText().toString().trim() + " date: " + handleTimeCount.getDate());
        model.setTotalAll(binding.edtTotalAll.getText().toString().trim());
//        model.setClientName(binding.cl.getText().toString().trim());
        model.setClientPhone(binding.edtClientPhone.getText().toString().trim());
        binding.switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.setType(isChecked ? "take" : "dues");
        });
        return model;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.add1Item.setOnClickListener(this);
        binding.createItem.setOnClickListener(this);
        binding.addClient.setOnClickListener(this);
        binding.createClient.setOnClickListener(this);

        binding.btnBack.setOnClickListener(this);
    }



    private void loadItems() {
        BottomSheetDialog itemBottomDialog = new BottomSheetDialog(requireContext());
        LayoutItemsBinding itemsBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),
                R.layout.layout_items, getView().findViewById(R.id.container_items), false);
        itemBottomDialog.setContentView(itemsBinding.getRoot());
        itemsBinding.recyclerItemsView.setAdapter(adapter);
        itemsBinding.imageClose.setOnClickListener(v1 -> {
            adapter.stopListening();
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
            adapter.startListening();
        } else {
            adapter.stopListening();
        }
    }

    private void loadClients() {
        BottomSheetDialog itemBottomDialog = new BottomSheetDialog(requireContext());
        LayoutClientBinding clientBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),
                R.layout.layout_client, getView().findViewById(R.id.container_client), false);
        itemBottomDialog.setContentView(clientBinding.getRoot());
        clientBinding.recyclerItemsView.setAdapter(adapterClient);
        clientBinding.imageClose.setOnClickListener(v1 -> {
            adapterClient.stopListening();
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
            adapterClient.startListening();
        } else {
            adapter.stopListening();
        }
    }


    private void deleteAllItem() {
//
    }

    private void totalAll() {

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (binding.createClient.getId() == id) {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentAddReceipt_to_fragmentCreateClient);
        } else if (R.id.create_item == id) {
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_fragmentAddReceipt_to_fragmentCreateItem);
            Log.d(TAG, "onClick: ");
        } else if (binding.btnBack.getId() == id) {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        } else if (binding.add1Item.getId() == id) {
            loadItems();
        } else if (binding.addClient.getId() == id) {
            loadClients();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        handleTimeCount.onPause();
    }

    @Override
    public void onClickItem(Items model) {

    }

    @Override
    public void onClient(Client model) {

    }
}