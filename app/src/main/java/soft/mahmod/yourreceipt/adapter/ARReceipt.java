package soft.mahmod.yourreceipt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemReceiptBinding;
import soft.mahmod.yourreceipt.listeners.OnReceiptItemClick;
import soft.mahmod.yourreceipt.model.Receipt;

public class ARReceipt extends RecyclerView.Adapter<ARReceipt.ViewHolder> {
    private LayoutInflater inflater;
    private List<Receipt> listModel;
    private OnReceiptItemClick itemClick;

    public ARReceipt(List<Receipt> listModel,OnReceiptItemClick itemClick) {
        this.listModel = listModel;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemReceiptBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_receipt, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ARReceipt.ViewHolder holder, int position) {

        holder.bind(listModel.get(position));
    }

    @Override
    public int getItemCount() {
        return listModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemReceiptBinding binding;

        public ViewHolder(@NonNull ItemReceiptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Receipt model) {
            binding.setModel(model);
            binding.goDetails.setOnClickListener(v -> {
                itemClick.itemClick(model);
            });
        }
    }
}
