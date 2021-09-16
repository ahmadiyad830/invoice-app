package soft.mahmod.yourreceipt.adapter.firebase;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.internal.ContextUtils;
import com.google.firebase.database.DatabaseError;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.ARPayment;
import soft.mahmod.yourreceipt.databinding.ItemReceiptBinding;
import soft.mahmod.yourreceipt.listeners.OnReceiptItemClick;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class ARReceipt extends FirebaseRecyclerAdapter<Receipt,ARReceipt.ViewHolder> implements DatabaseUrl {
    private static final String TAG = "ARReceipt";
    private LayoutInflater inflater;
    private OnReceiptItemClick itemClick;
    private Context context;
    public ARReceipt(@NonNull FirebaseRecyclerOptions<Receipt> options,OnReceiptItemClick onReceiptItemClick) {
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
        holder.binding.containerPayment.setVisibility(isExpandedPayment? View.VISIBLE:View.GONE);
        holder.binding.containerDateDept.setVisibility(isExpandedDeptReceipt? View.VISIBLE:View.GONE);
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ARPayment.ListenerOnClick {
        private ItemReceiptBinding binding;
        ARPayment arPayment ;
        public ViewHolder(@NonNull ItemReceiptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Receipt model) {
            extracted(model);
            binding.setModel(model);
            binding.btnDetails.setOnClickListener(v -> {
                itemClick.itemClick(model);
            });
            binding.constraintLayout.setOnClickListener(v -> {
                typePayment(model);
                notifyItemChanged(getAbsoluteAdapterPosition());
            });
            colorTypePayment(model.getPayment());

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

        private void extracted(Receipt model) {
            binding.recPayment.setHasFixedSize(true);
            arPayment = new ARPayment(model.getPayment().getListPayment(),this);
            binding.recPayment.setAdapter(arPayment);
            arPayment.setIsCreate(View.GONE);
        }

        @Override
        public void payment(Payment model) {

        }

        @Override
        public void deletePayment(int position) {

        }

        @Override
        public void paid(boolean isChecked, int position) {
            getRef(getAbsoluteAdapterPosition())
                    .child(PAYMENT)
                    .child(LIST_PAYMENT)
                    .child(String.valueOf(position))
                    .child(PAID)
                    .setValue(isChecked);
        }

        private void typePayment(Receipt model){
            if (model.getPayment().getTypePayment().equals(context.getResources().getString(R.string.debt))){
                model.setExpandedDeptReceipt(!model.isExpandedDeptReceipt());
            }else {
                model.setExpandedPayment(!model.isExpandedPayment());
            }
        }
    }
}
