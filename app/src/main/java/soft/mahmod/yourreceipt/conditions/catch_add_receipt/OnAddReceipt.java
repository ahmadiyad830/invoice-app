package soft.mahmod.yourreceipt.conditions.catch_add_receipt;


import java.util.List;

public interface OnAddReceipt {
    interface OnProducts {
        void sizeListProducts(int size);
    }

    interface OnClient {
        void clientNameOrID(String clientName, String clientNameByID);
    }

    interface OnReceipt {
        <T> void totalAll(T number);

        <T> void discount(T number);
    }
}
