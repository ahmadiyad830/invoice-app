package soft.mahmod.yourreceipt.view_fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARReceipt;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.databinding.FragmentHomeBinding;
import soft.mahmod.yourreceipt.listeners.OnReceiptItemClick;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.repository.home.RepoHome;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_activity.ActivityDetails;
import soft.mahmod.yourreceipt.view_model.home.VMReceipt;


public class FragmentHome extends Fragment implements OnReceiptItemClick , DatabaseUrl {
    private static final String TAG = "FragmentHome";
    private FragmentHomeBinding binding;
    private ARReceipt adapter;
    private DatabaseReference reference;
    private FirebaseRecyclerOptions<Receipt> options;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        reference = FirebaseDatabase.getInstance().getReference();
        init();

        loadReceipt();


        binding.swipeList.setOnRefreshListener(this::loadReceipt);
        return binding.getRoot();
    }


    private void loadReceipt() {
        adapter.startListening();
        binding.swipeList.setRefreshing(false);
    }

    private void init() {
        binding.mainRecycler.setHasFixedSize(true);
        binding.mainRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        options = new FirebaseRecyclerOptions.Builder<Receipt>().setQuery
                (reference.child(ALL_RECEIPT + FirebaseAuth.getInstance().getUid()), Receipt.class)
                .build();
        adapter = new ARReceipt(options, this);
        binding.mainRecycler.setAdapter(adapter);
    }


    @Override
    public void itemClick(Receipt model) {
        Intent intent = new Intent(requireContext(), ActivityDetails.class);
//        intent.putExtra("model",model);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }
}