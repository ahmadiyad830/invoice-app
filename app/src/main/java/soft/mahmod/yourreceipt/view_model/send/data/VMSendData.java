package soft.mahmod.yourreceipt.view_model.send.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Receipt;

public class VMSendData extends ViewModel {
    private MutableLiveData<Receipt> receiptData = new MutableLiveData<>();
    private MutableLiveData<Client> clientDate = new MutableLiveData<>();
    private Receipt receipt;
    private Client client;
    public LiveData<Receipt> getReceipt() {
        return receiptData;
    }
    public LiveData<Client> getClient() {
        return clientDate;
    }

    public void passReceipt(Receipt model) {
        this.receipt = model;
        receiptData.setValue(model);
    }
    public void passClient(Client model) {
        this.client = model;
        clientDate.setValue(model);
    }

}
