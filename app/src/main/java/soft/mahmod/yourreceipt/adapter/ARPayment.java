package soft.mahmod.yourreceipt.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemsBaymentBinding;
import soft.mahmod.yourreceipt.model.billing.Payment;

public class ARPayment extends RecyclerView.Adapter<ARPayment.ViewHolder> {
    public static final String TAG = "ARPayment";

    public interface ListenerOnClick {
        void payment(Payment model);

        void deletePayment(int position);
    }

    private LayoutInflater inflater;
    private List<Payment> listPayment;
    private ListenerOnClick listener;

    public ARPayment(List<Payment> listPayment, ListenerOnClick listener) {
        this.listPayment = listPayment;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemsBaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.items_bayment, parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (listPayment.size() > 0)
            holder.bind(listPayment.get(position),position);
    }


    @Override
    public int getItemCount() {
        return listPayment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemsBaymentBinding binding;

        public ViewHolder(ItemsBaymentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Payment model,int position) {
            int index = position + 1;
            binding.setIndex(index);
            binding.setModel(model);
            listener.payment(model);
            binding.btnDelete.setOnClickListener(v -> {
                listener.deletePayment(getBindingAdapterPosition());
            });
        }
    }
}