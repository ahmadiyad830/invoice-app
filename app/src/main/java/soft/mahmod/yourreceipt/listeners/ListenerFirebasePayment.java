package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.billing.Payment;

public interface ListenerFirebasePayment {

    void onEdit(Payment model, int position);
}
