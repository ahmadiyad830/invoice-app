package soft.mahmod.yourreceipt.view_model.ui;

import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.CreateReceipt;

public class VMCreateReceipt {
    private CreateReceipt createReceipt;

    public VMCreateReceipt() {
        createReceipt = new CreateReceipt();
    }

    public LiveData<CreateReceipt> createReceiptLiveData() {
        return createReceipt.data();
    }

    public void subject(CreateReceipt model) {
        createReceipt.data().setValue(model);
    }
}
