package soft.mahmod.yourreceipt.adapter.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemReceiptBinding;
import soft.mahmod.yourreceipt.listeners.ListenerReceipt;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class ARFirebaseReceipt extends FirebaseRecyclerAdapter<Receipt, ARFirebaseReceipt.ViewHolder> implements DatabaseUrl {
    private static final String TAG = "ARReceipt";
    private LayoutInflater inflater;
    private ListenerReceipt itemClick;
    private Context context;

    public ARFirebaseReceipt(@NonNull FirebaseRecyclerOptions<Receipt> options, ListenerReceipt onReceiptItemClick) {
        super(options);
        this.itemClick = onReceiptItemClick;
    }


    @Override
    public int getItemCount() {
        return super.getSnapshots().size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
            parent.getContext().getString(R.string.account);
        }
        ItemReceiptBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_receipt, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Receipt model) {
        holder.bind(model);
        boolean isExpandedPayment = model.isExpandedPayment();
        boolean isExpandedDeptReceipt = model.isExpandedDeptReceipt();
//        holder.binding.containerPayment.setVisibility(isExpandedPayment? View.VISIBLE:View.GONE);
        holder.binding.containerDateDept.setVisibility(isExpandedDeptReceipt? View.VISIBLE:View.GONE);
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemReceiptBinding binding;

        public ViewHolder(@NonNull ItemReceiptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Receipt model) {
            binding.setModel(model);
            binding.btnDetails.setOnClickListener(v -> {
                itemClick.onClick(model);
            });
            binding.constraintLayout.setOnClickListener(v -> {
                itemClick.onClick(model);
            });
            binding.constraintLayout.setOnLongClickListener(v -> {
                itemClick.onLongClick(getBindingAdapterPosition());
                return false;
            });
            if (model.getPayment() != null) {
                colorTypePayment(model.getPayment());
            }else {
                binding.txtTypeReceipt.setVisibility(View.GONE);
            }
        }

        private void colorTypePayment(Payment payment) {
            if (payment.getTypePayment().equals(context.getResources().getString(R.string.paid))){
                binding.txtTypeReceipt.setTextColor(context.getResources().getColor(R.color.paid_color,null));
            }else if (payment.getTypePayment().equals(context.getResources().getString(R.string.debt))){
                binding.txtTypeReceipt.setTextColor(context.getResources().getColor(R.color.dept_color,null));
            }else if (payment.getTypePayment().equals(context.getResources().getString(R.string.bayment))){
                binding.txtTypeReceipt.setTextColor(context.getResources().getColor(R.color.dept_color,null));
            }
        }


        private void typePayment(Receipt model) {
            if (model.getPayment().getTypePayment().equals(context.getResources().getString(R.string.debt))) {
                model.setExpandedDeptReceipt(!model.isExpandedDeptReceipt());
            } else {
                model.setExpandedPayment(!model.isExpandedPayment());
            }
        }


//        getRef(getAbsoluteAdapterPosition())
//                .child(PAYMENT)
//                    .child(LIST_PAYMENT)
//                    .child(String.valueOf(position))
//                .child(PAID)
//                    .setValue(isChecked);

    }
}
