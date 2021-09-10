package soft.mahmod.yourreceipt.adapter.firebase;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemItemsBinding;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;

public class ARItems extends FirebaseRecyclerAdapter<Items, ARItems.ViewHolder> {
    public interface OnCLickItem {
        void clickItemToCreateProduct(Products model, Items itemModel, int position);

        void editItem(Items model);

    }

    private LayoutInflater inflater;
    private OnCLickItem listener;

    public ARItems(@NonNull FirebaseRecyclerOptions<Items> options, OnCLickItem listener) {
        super(options);
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemItemsBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_items, parent, false);
        return new ViewHolder(binding);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Items model) {
        holder.bind(model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemItemsBinding binding;

        public ViewHolder(@NonNull ItemItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Items model) {
            binding.setModel(model);
            binding.getRoot().setOnClickListener(v -> {
                listener.clickItemToCreateProduct(getProducts(model),model, getBindingAdapterPosition());
            });
            binding.goDetails.setOnClickListener(v -> {
                listener.editItem(model);
            });
        }

        private Products getProducts(Items model) {
            Products products = new Products();
            products.setName(model.getName());
            products.setPrice(model.getPrice());
            products.setQuantity(model.getQuantity());
            products.setTotal(products.getPrice() * products.getQuantity());
            return products;
        }
    }
}
