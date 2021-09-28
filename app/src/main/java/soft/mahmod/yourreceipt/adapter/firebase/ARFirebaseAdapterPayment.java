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
import soft.mahmod.yourreceipt.listeners.ListenerPayment;
import soft.mahmod.yourreceipt.model.billing.Payment;


public class ARFirebaseAdapterPayment extends FirebaseRecyclerAdapter<Payment, ARFirebaseAdapterPayment.ViewHolde> {
    private ListenerPayment listener;
    private LayoutInflater inflater;

    public ARFirebaseAdapterPayment(@NonNull FirebaseRecyclerOptions<Payment> options, ListenerPayment listener) {
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
                onDelete();
            });
            binding.editDate.setOnClickListener(v -> {
                onChangeDate();
            });
            binding.editPrice.setOnClickListener(v -> {
                onChangePrice();
            });
            binding.switchPaid.setOnCheckedChangeListener(this::onSwithcPaid);

        }
        public void onDelete(){
            listener.onDelete(getBindingAdapterPosition());
        }
        public void onChangeDate(){
            listener.onChangeDate(getBindingAdapterPosition());
        }
        public void onChangePrice(){
            listener.onChangeDate(getBindingAdapterPosition());
        }
        public void onSwithcPaid(CompoundButton buttonView, boolean isChecked){
            listener.onPaid(buttonView,isChecked,getBindingAdapterPosition());
        }

    }
}
