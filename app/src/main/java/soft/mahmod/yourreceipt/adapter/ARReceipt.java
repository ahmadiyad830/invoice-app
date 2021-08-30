package soft.mahmod.yourreceipt.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;

import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemReceiptBinding;
import soft.mahmod.yourreceipt.listeners.OnReceiptItemClick;
import soft.mahmod.yourreceipt.model.Receipt;

public class ARReceipt extends FirebaseRecyclerAdapter<Receipt,ARReceipt.ViewHolder> {
    private static final String TAG = "ARReceipt";
    private LayoutInflater inflater;
    private OnReceiptItemClick itemClick;

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
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemReceiptBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_receipt, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Receipt model) {
        holder.bind(model);
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
            binding.goDetails.setOnClickListener(v -> {
                itemClick.itemClick(model);
            });
        }
    }
}
