package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.Receipt;

public interface ListenerReceipt extends MainListener<Receipt>{
    @Override
    void onClick(Receipt model);

    @Override
    void onEdit(Receipt model, int position);

    void onLongClick(int position);
}
