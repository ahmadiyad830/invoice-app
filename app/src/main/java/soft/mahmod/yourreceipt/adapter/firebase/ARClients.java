package soft.mahmod.yourreceipt.adapter.firebase;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.databinding.ItemClientBinding;
import soft.mahmod.yourreceipt.model.Client;

public class ARClients extends FirebaseRecyclerAdapter<Client, ARClients.ViewHolder> {
    public interface OnClickClient{
        void clickClient(Client model,int position);
        void editClient(Client model);
    }
    private LayoutInflater inflater;

    private OnClickClient listener;

    public ARClients(@NonNull FirebaseRecyclerOptions<Client> options, OnClickClient listener) {
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
                listener.clickClient(model,getBindingAdapterPosition());
            });
            binding.goEdit.setOnClickListener(v -> {
                listener.editClient(model);
            });
        }
    }
}
