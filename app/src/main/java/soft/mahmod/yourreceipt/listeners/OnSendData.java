package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.Receipt;

public interface OnSendData extends OnClickItemListener{
    void onSend(Receipt model);

}
