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
import soft.mahmod.yourreceipt.databinding.ItemItemsBinding;
import soft.mahmod.yourreceipt.listeners.ListenerItems;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;

public class ARItems extends FirebaseRecyclerAdapter<Items, ARItems.ViewHolder> {
    private static final String TAG = "ARItems";

    private LayoutInflater inflater;
    private ListenerItems listener;

    public ARItems(@NonNull FirebaseRecyclerOptions<Items> options, ListenerItems listener) {
        super(options);
        this.listener = listener;
    }


    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
        Log.d(TAG, "onError: "+error.getMessage()+"\n"+error.getCode()+"\n"+error.getDetails());
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
                listener.onClick(getProducts(model), model,getBindingAdapterPosition());
            });
        }

        private Products getProducts(Items model) {
            Products products = new Products();
            products.setName(model.getName());
            products.setPrice(model.getPrice());
            products.setQuantity(model.getQuantity());
            products.setTax(model.getTax());
            products.setTotal(products.getPrice() * products.getQuantity());
            return products;
        }
    }
}
