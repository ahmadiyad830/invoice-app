package soft.mahmod.yourreceipt.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemClientBinding;
import soft.mahmod.yourreceipt.databinding.ItemItemsBinding;
import soft.mahmod.yourreceipt.listeners.OnClickItemListener;
import soft.mahmod.yourreceipt.listeners.OnClientClick;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Items;

public class ARClients extends FirebaseRecyclerAdapter<Client, ARClients.ViewHolder> {
    private LayoutInflater inflater;

    private OnClientClick listener;

    public ARClients(@NonNull FirebaseRecyclerOptions<Client> options, OnClientClick listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Client model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemClientBinding binding = DataBindingUtil.inflate(inflater,R.layout.item_client,parent,false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemClientBinding binding;

        public ViewHolder(@NonNull ItemClientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Client model) {
            binding.setModel(model);
            binding.goDetails.setOnClickListener(v -> {
                listener.onClient(model);
            });
        }
    }
}
