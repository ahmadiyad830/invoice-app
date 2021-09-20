package soft.mahmod.yourreceipt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

        void paid (boolean isChecked,int position);
    }

    public void setListPayment(List<Payment> listPayment) {
        this.listPayment = listPayment;
    }

    private LayoutInflater inflater;
    private List<Payment> listPayment;
    private ListenerOnClick listener;
    private int isCreate = View.VISIBLE;
    public ARPayment(List<Payment> listPayment, ListenerOnClick listener) {
        this.listPayment = listPayment;
        this.listener = listener;
    }

    public ARPayment(List<Payment> listPayment) {
        this.listPayment = listPayment;
    }

    public int getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(int isCreate) {
        this.isCreate = isCreate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        ItemsBaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.items_bayment, parent, false);
        binding.btnDelete.setVisibility(getIsCreate());
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (listPayment.size() > 0 && listPayment!=null)
            holder.bind(listPayment.get(position),position);
    }


    @Override
    public int getItemCount() {
        int size;
        try {
            size = listPayment.size();
        } catch (NullPointerException e){
            size = 0;
        }
        return size;
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
            binding.switchPaid.setOnCheckedChangeListener((buttonView, isChecked) -> {
                listener.paid(isChecked,position);
            });
        }
    }
}