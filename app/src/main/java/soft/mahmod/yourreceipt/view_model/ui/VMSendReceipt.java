package soft.mahmod.yourreceipt.view_model.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Receipt;

public class VMSendReceipt extends ViewModel {
    private MutableLiveData<Receipt> data = new MutableLiveData<>();
    private Receipt model;

    public LiveData<Receipt> getModel() {
        return data;
    }

    public void setModel(Receipt model) {
        this.model = model;
        data.setValue(model);
    }
}
