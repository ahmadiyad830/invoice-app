package soft.mahmod.yourreceipt.listeners;

import soft.mahmod.yourreceipt.model.billing.Payment;

public interface ListenerPayment extends MainListener<Payment> {
    @Override
    void onClick(Payment model);

    @Override
    void onEdit(Payment model, int position);

    void onDelete(int position);

    void onPaid(boolean isChecked , int position);

    void onChangeDate(String date,int position);

    void onChangePrice(double price,int position);
}
