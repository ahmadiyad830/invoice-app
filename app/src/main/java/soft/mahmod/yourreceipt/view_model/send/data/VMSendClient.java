package soft.mahmod.yourreceipt.view_model.send.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Client;

public class VMSendClient extends ViewModel {
    private MutableLiveData<Client> data = new MutableLiveData<>();
    private Client model;

    public LiveData<Client> getModel() {
        return data;
    }

    public void setModel(Client model) {
        this.model = model;
        data.setValue(model);
    }
}
