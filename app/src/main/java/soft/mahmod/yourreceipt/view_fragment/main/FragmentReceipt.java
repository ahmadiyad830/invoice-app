package soft.mahmod.yourreceipt.view_fragment.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.firebase.ARReceipt;
import soft.mahmod.yourreceipt.databinding.FragmentReceiptBinding;
import soft.mahmod.yourreceipt.dialog.DialogSecurity;
import soft.mahmod.yourreceipt.helper.SimpleDialog;
import soft.mahmod.yourreceipt.listeners.ListenerReceipt;
import soft.mahmod.yourreceipt.listeners.ListenerSecurityDialog;
import soft.mahmod.yourreceipt.listeners.SimpleDialogListener;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.view_activity.ActivityDetails;


public class FragmentReceipt extends Fragment implements ListenerReceipt, DatabaseUrl, AdapterView.OnItemSelectedListener
        , TextWatcher, SimpleDialogListener {
    private static final String TAG = "FragmentHome";
    private FragmentReceiptBinding binding;
    private ARReceipt adapter;
    private String[] sortReceipt = {"clientName", "subject", "clientPhone", "totalAll"};
    private String key = sortReceipt[0];
    private Query query;
    private FirebaseRecyclerOptions<Receipt> options;
    private DatabaseReference reference;
    private NavController controller;
    private DialogSecurity dialogSecurity;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference()
                .child(RECEIPT + FirebaseAuth.getInstance().getUid());
        withoutSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt, container, false);
        dialogSecurity = new DialogSecurity(requireContext(), getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        spinnerInit();
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        binding.btnDelete.setOnClickListener(v -> {
            binding.textSearch.setText("");
        });
        binding.textSearch.addTextChangedListener(this);
        adapter.startListening();
    }

    private void init() {
        binding.mainRecycler.setHasFixedSize(true);
        binding.mainRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.mainRecycler.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        key = ((String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        key = sortReceipt[0];
    }
    private ARReceipt searchNumber(double search) {
        query = reference;
        options = new FirebaseRecyclerOptions.Builder<Receipt>()
                .setQuery(query.orderByChild(key).startAt(search).endAt(search + "\uf8ff"), Receipt.class)
                .build();
        adapter = new ARReceipt(options, this);
        adapter.startListening();
        return adapter;
    }

    private ARReceipt search(String search) {
        query = reference;
        options = new FirebaseRecyclerOptions.Builder<Receipt>()
                .setQuery(query.orderByChild(key).startAt(search).endAt(search + "\uf8ff"), Receipt.class)
                .build();
        adapter = new ARReceipt(options, this);
        adapter.startListening();
        return adapter;
    }

    private ARReceipt withoutSearch() {
        options = new FirebaseRecyclerOptions.Builder<Receipt>()
                .setQuery(reference, Receipt.class)
                .build();
        adapter = new ARReceipt(options, this);
        adapter.startListening();
        return adapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String search = s.toString().trim();
        if (!search.isEmpty()) {
            if (!key.equals(sortReceipt[0]) && !key.equals(sortReceipt[1])) {
                try {
                    binding.mainRecycler.setAdapter(searchNumber(Double.parseDouble(search)));
                }catch (NumberFormatException exception){
                    Toast.makeText(requireContext(), getResources().getString(R.string.error_input_charcter), Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.mainRecycler.setAdapter(search(search));
            }
        } else {
            binding.mainRecycler.setAdapter(withoutSearch());
        }
        binding.setEmptyTextSearch(!search.isEmpty());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }
    private void spinnerInit() {
        binding.spinnerSortList.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>
                (requireContext(), R.layout.spinner_style, sortReceipt);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSortList.setAdapter(adapter);
    }

    @Override
    public void onClick(Receipt model) {
        Intent intent = new Intent(requireContext(), ActivityDetails.class);
        intent.putExtra("model", model);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onEdit(Receipt model, int position) {

    }
    private int position;
    @Override
    public void onLongClick(int position) {
        this.position = position;
        if (!dialogSecurity.hasKey()) {
            SimpleDialog.simpleDialog(
                    requireContext()
                    , R.string.warning
                    , R.string.warning_add_key
                    , R.drawable.ic_twotone_warning_24
                    , this
            );
            return;
        }else if (!dialogSecurity.showDialog()){
            SimpleDialog.simpleDialog(
                    requireContext()
                    , R.string.warning
                    , R.string.warning_add_key
                    , R.drawable.ic_twotone_warning_24
                    , this
            );

            return;
        }
        dialogSecurity.securityDialog(new ListenerSecurityDialog() {
            @Override
            public void onOk(Dialog dialog, boolean notWrong) {
                if (notWrong){
                    deleteReceipt(position);
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancel(Dialog dialog) {
                dialog.dismiss();
            }
        });

    }

    private void deleteReceipt(int position) {
        adapter.getRef(position).removeValue();
    }

    @Override
    public void clickOk(DialogInterface dialog) {
        deleteReceipt(position);
    }
}