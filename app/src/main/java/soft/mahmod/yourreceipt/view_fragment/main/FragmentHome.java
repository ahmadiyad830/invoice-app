package soft.mahmod.yourreceipt.view_fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARReceipt;
import soft.mahmod.yourreceipt.databinding.FragmentHomeBinding;
import soft.mahmod.yourreceipt.listeners.OnReceiptItemClick;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_activity.ActivityDetails;


public class FragmentHome extends Fragment implements OnReceiptItemClick , DatabaseUrl , AdapterView.OnItemSelectedListener{
    private static final String TAG = "FragmentHome";
    private FragmentHomeBinding binding;
    private ARReceipt adapter;
    private DatabaseReference reference;
    private FirebaseRecyclerOptions<Receipt> options;
    private String[] sort = {"client", "items", "receipt"};
    private String key = sort[0];

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference();
        options = new FirebaseRecyclerOptions.Builder<Receipt>().setQuery
                (reference.child(RECEIPT + FirebaseAuth.getInstance().getUid()), Receipt.class)
                .build();
        adapter = new ARReceipt(options, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);

        init();

        return binding.getRoot();
    }



    private void init() {
        binding.mainRecycler.setHasFixedSize(true);
        binding.mainRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.mainRecycler.setAdapter(adapter);
    }


    @Override
    public void itemClick(Receipt model) {
        Intent intent = new Intent(requireContext(), ActivityDetails.class);
//        intent.putExtra("model",model);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}