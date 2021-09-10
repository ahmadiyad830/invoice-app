package soft.mahmod.yourreceipt.adapter.firebase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemProductBinding;
import soft.mahmod.yourreceipt.model.Products;

public class ARProduct extends FirebaseRecyclerAdapter<Products, ARProduct.ViewHolder> {
    private static final String TAG = "ARProduct";

    public interface OnClickItem {
        void editProduct(Products model, int position);

        <T> void editSingleProduct(String name, String key, T value, boolean type, int position);

        void deleteProduct(Products model, int position);

        void addProduct(Products model);

        void clickProduct(Products model, int position);
    }

    private LayoutInflater inflater;
    private OnClickItem onClickItem;

    public ARProduct(@NonNull FirebaseRecyclerOptions<Products> options, OnClickItem onClickItem) {
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


    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
        Log.d(TAG, "onError: " + error.getMessage());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

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
                onClickItem.clickProduct(model, getBindingAdapterPosition());
            });
            binding.btnEdit.setOnClickListener(v -> {
                onClickItem.editProduct(model, getBindingAdapterPosition());
            });

            binding.name.setOnClickListener(v -> {
                onClickItem.editSingleProduct("name", "itemName", model.getName()
                        ,false, getBindingAdapterPosition());
            });
            binding.price.setOnClickListener(v -> {
                onClickItem.editSingleProduct("price", "productsPrice", model.getPrice()
                        , true, getBindingAdapterPosition());
            });
            binding.quantity.setOnClickListener(v -> {
                onClickItem.editSingleProduct("quantity", "productsQuantity", model.getQuantity()
                        , true, getBindingAdapterPosition());
            });
            binding.total.setOnClickListener(v -> {
                onClickItem.editSingleProduct("total", "total", model.getTotal()
                        , true, getBindingAdapterPosition());
            });
        }


    }
}
