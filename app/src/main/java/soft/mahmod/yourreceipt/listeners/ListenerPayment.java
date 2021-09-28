package soft.mahmod.yourreceipt.listeners;

import android.widget.CompoundButton;

import soft.mahmod.yourreceipt.model.billing.Payment;

public interface ListenerPayment extends MainListener<Payment> {
    @Override
    void onClick(Payment model);

    @Override
    void onEdit(Payment model, int position);

    void onDelete(int position);

    void onPaid(CompoundButton buttonView, boolean isChecked, int position);

    void onChangeDate(int position);

    void onChangePrice(int position);
}
