package soft.mahmod.yourreceipt.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemProductBinding;
import soft.mahmod.yourreceipt.model.Products;

public class ARProducts extends RecyclerView.Adapter<ARProducts.ViewHolder> {

    public interface OnClickItem {
        void clickProduct(Products model, int position);

        void deleteProduct(Products model, int position);

    }

    private LayoutInflater inflater;
    private final List<Products> listModel;
    private OnClickItem onClickItem;

    public ARProducts(List<Products> listModel, OnClickItem onClickItem) {
        this.listModel = listModel;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemProductBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_product, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ARProducts.ViewHolder holder, int position) {
        holder.bind(listModel.get(position));
    }

    @Override
    public int getItemCount() {
        return listModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;

        public ViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products model) {
            binding.setModel(model);
            binding.btnDelete.setOnClickListener(v -> {
                onClickItem.deleteProduct(model, getBindingAdapterPosition());
            });
            binding.getRoot().setOnClickListener(v -> {
                onClickItem.clickProduct(model, getBindingAdapterPosition());

            });
        }
    }
}
