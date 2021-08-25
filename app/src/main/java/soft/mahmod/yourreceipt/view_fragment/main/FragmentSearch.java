package soft.mahmod.yourreceipt.view_fragment.main;

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
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARReceipt;
import soft.mahmod.yourreceipt.databinding.FragmentSearchBinding;
import soft.mahmod.yourreceipt.listeners.OnReceiptItemClick;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_activity.ActivityDetails;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment implements OnReceiptItemClick, DatabaseUrl {
    private static final String TAG = "FragmentSearch";
    private FragmentSearchBinding binding;
    private ARReceipt adapter;
    private DatabaseReference reference;
    private FirebaseRecyclerOptions<Receipt> options;
    private boolean hasValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        init("");
        binding.btnSearch.setOnClickListener(v -> {
            binding.setIsSearch(true);
            String client = binding.textSearch.getText().toString().trim();
            init(client);
        });
        return binding.getRoot();
    }

    private void init(String client) {
        reference = FirebaseDatabase.getInstance().getReference();
        options = new FirebaseRecyclerOptions.Builder<Receipt>()
                .setQuery(reference.child(ALL_RECEIPT + FirebaseAuth.getInstance().getUid())
                        .orderByChild("clientName").startAt(client).endAt(client + "\uf8ff"), Receipt.class)
                .build();
        adapter = new ARReceipt(options, this);
        binding.searchRecycler.setHasFixedSize(true);
        binding.searchRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.searchRecycler.setAdapter(adapter);
        adapter.startListening();
        binding.setHasValue(true);
    }

    @Override
    public void itemClick(Receipt model) {
        Intent intent = new Intent(requireContext(), ActivityDetails.class);
        startActivity(intent);
    }
}