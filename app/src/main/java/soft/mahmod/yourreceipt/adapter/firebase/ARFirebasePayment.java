package soft.mahmod.yourreceipt.adapter.firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemsBaymentBinding;
import soft.mahmod.yourreceipt.listeners.ListenerFirebasePayment;
import soft.mahmod.yourreceipt.listeners.ListenerPayment;
import soft.mahmod.yourreceipt.model.billing.Payment;


public class ARFirebasePayment extends FirebaseRecyclerAdapter<Payment, ARFirebasePayment.ViewHolde> {
    private ListenerFirebasePayment listener;
    private LayoutInflater inflater;
    public boolean inDetails = true;
    public ARFirebasePayment(@NonNull FirebaseRecyclerOptions<Payment> options, ListenerFirebasePayment listener) {
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemsBaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.items_bayment, parent, false);
        binding.setInDetails(inDetails);
        return new ViewHolde(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolde holder, int position, @NonNull Payment model) {
        holder.bind(model);
    }



    public class ViewHolde extends RecyclerView.ViewHolder {
        private ItemsBaymentBinding binding;

        public ViewHolde(ItemsBaymentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Payment model){
            binding.setModel(model);
            binding.btnDelete.setOnClickListener(v -> {
                listener.onEdit(model,getBindingAdapterPosition());
            });
        }

    }
}
