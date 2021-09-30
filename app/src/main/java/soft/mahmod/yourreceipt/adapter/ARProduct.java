
package soft.mahmod.yourreceipt.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.DoubleStream;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemProductBinding;
import soft.mahmod.yourreceipt.listeners.ListenerProduct;
import soft.mahmod.yourreceipt.model.Products;

public class ARProduct extends RecyclerView.Adapter<ARProduct.ViewHolder> {

    private LayoutInflater inflater;
    private final List<Products> listModel;
    private ListenerProduct onClickItem;
    public double totalAll = 0.0;
    public boolean isCreate = false;

    public ARProduct(List<Products> listModel, ListenerProduct onClickItem) {
        this.listModel = listModel;
        this.onClickItem = onClickItem;
    }

    public boolean isCreate() {
        return isCreate;
    }

    public void setCreate(boolean create) {
        isCreate = create;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemProductBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_product, parent, false);
        binding.setIsCreate(isCreate());
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ARProduct.ViewHolder holder, int position) {
        holder.bind(listModel.get(position));
        double total = DoubleStream.of(listModel.get(position).getPrice()).sum() *
                DoubleStream.of(listModel.get(position).getQuantity()).sum();
        totalAll = total;
    }

    @Override
    public int getItemCount() {
        return listModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;
        private double total;
        public ViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public synchronized void bind(Products model) {
            binding.setModel(model);
            binding.btnEdit.setOnClickListener(v -> {
                total = total - model.getPrice();
                onClickItem.onDelete(model, getBindingAdapterPosition());
            });
            binding.getRoot().setOnClickListener(v -> {
                onClickItem.onEdit(model, getBindingAdapterPosition());
            });
        }
    }
}
