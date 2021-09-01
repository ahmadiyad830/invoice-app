package soft.mahmod.yourreceipt.adapter.firebase;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemProductBinding;
import soft.mahmod.yourreceipt.model.Products;

public class ARProduct extends FirebaseRecyclerAdapter<Products, ARProduct.ViewHolder> {
    public interface OnClickItem {
        void editProduct(Products model, int position);

        void deleteProduct(Products model, int position);

        void addProduct(Products model);
    }
    private LayoutInflater inflater;
    private OnClickItem onClickItem;
    public ARProduct(@NonNull FirebaseRecyclerOptions<Products> options,OnClickItem onClickItem) {
        super(options);
        this.onClickItem = onClickItem;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Products model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemProductBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_product, parent, false);
        return new ARProduct.ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding ;
        public ViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public synchronized void bind(Products model) {
            binding.setModel(model);
            binding.btnDelete.setOnClickListener(v -> {
                onClickItem.deleteProduct(model, getBindingAdapterPosition());
            });
            binding.getRoot().setOnClickListener(v -> {
                onClickItem.editProduct(model, getBindingAdapterPosition());
            });
        }
    }
}
