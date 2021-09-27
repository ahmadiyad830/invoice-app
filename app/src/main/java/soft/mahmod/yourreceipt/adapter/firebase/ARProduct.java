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
import soft.mahmod.yourreceipt.listeners.ListenerProduct;
import soft.mahmod.yourreceipt.model.Products;

public class ARProduct extends FirebaseRecyclerAdapter<Products, ARProduct.ViewHolder> {
    private static final String TAG = "ARProduct";
    public boolean isCreate = false;

    private LayoutInflater inflater;
    private ListenerProduct onClickItem;

    public ARProduct(@NonNull FirebaseRecyclerOptions<Products> options, ListenerProduct onClickItem) {
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
        binding.setIsCreate(isCreate);
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
                onClickItem.onDelete(model, getBindingAdapterPosition());
            });
            binding.getRoot().setOnClickListener(v -> {
                onClickItem.onClick(model);
            });
//            binding.btnEdit.setOnClickListener(v -> {
//                onClickItem.editProduct(model, getBindingAdapterPosition());
//            });

//            binding.name.setOnClickListener(v -> {
//                onClickItem.editSingleProduct("name", "itemName", model.getName()
//                        ,false, getBindingAdapterPosition());
//            });
//            binding.price.setOnClickListener(v -> {
//                onClickItem.editSingleProduct("price", "productsPrice", model.getPrice()
//                        , true, getBindingAdapterPosition());
//            });
//            binding.quantity.setOnClickListener(v -> {
//                onClickItem.editSingleProduct("quantity", "productsQuantity", model.getQuantity()
//                        , true, getBindingAdapterPosition());
//            });
//            binding.total.setOnClickListener(v -> {
//                onClickItem.editSingleProduct("total", "total", model.getTotal()
//                        , true, getBindingAdapterPosition());
//            });
        }


    }
}
