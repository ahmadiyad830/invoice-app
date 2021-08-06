package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.FragmentAddReceiptBinding;
import soft.mahmod.yourreceipt.model.CreateReceipt;
import soft.mahmod.yourreceipt.utils.HandleTimeCount;
import soft.mahmod.yourreceipt.view_activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddReceipt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddReceipt extends Fragment implements View.OnClickListener {
    private FragmentAddReceiptBinding binding;
    private HandleTimeCount handleTimeCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_receipt, container, false);
        CreateReceipt model = new CreateReceipt();
        handleTimeCount = new HandleTimeCount();
        handleTimeCount.getDate();
        handleTimeCount.setTv_time(binding.txtTime);
        handleTimeCount.countDownStart();
        binding.setDate(handleTimeCount.getDate());

        binding.switchPrint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.setChecked(isChecked);
        });
        return binding.getRoot();
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