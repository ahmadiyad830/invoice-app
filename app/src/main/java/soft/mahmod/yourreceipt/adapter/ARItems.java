package soft.mahmod.yourreceipt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemItemsBinding;
import soft.mahmod.yourreceipt.listeners.OnClickItemListener;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Receipt;

public class ARItems extends FirebaseRecyclerAdapter<Items,ARItems.ViewHolder>{
    private LayoutInflater inflater;
    private OnClickItemListener<Items> listener;

    public ARItems(@NonNull FirebaseRecyclerOptions<Items> options,OnClickItemListener<Items> listener) {
        super(options);
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemItemsBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_items,parent,false);
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
        public void bind(Items model){
            binding.setModel(model);
            binding.goDetails.setOnClickListener(v -> {
                listener.onClickItem(model);
            });
        }
    }
}
