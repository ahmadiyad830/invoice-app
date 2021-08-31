package soft.mahmod.yourreceipt.conditions.catch_add_receipt;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import soft.mahmod.yourreceipt.model.Cash;

public class RulesAddReceipt implements OnAddReceipt.OnReceipt, OnAddReceipt.OnClient, OnAddReceipt.OnProducts {
    private MutableLiveData<Cash> errorData;
    private Cash cash;

    public RulesAddReceipt() {
        errorData = new MutableLiveData<>();
        cash = new Cash();
    }

    @Override
    public <T> void totalAll(T number) {
        if (number.toString().isEmpty()) {
            cash.setMessage("number is empty add total");
            cash.setError(true);
            errorData.setValue(cash);
        }
    }

    @Override
    public <T> void discount(T number) {
        if (number.toString().isEmpty()) {
            cash.setMessage("number is empty add discount");
            cash.setError(true);
            errorData.setValue(cash);
        }
    }

    // check if user add product
    @Override
    public void sizeListProducts(int size) {
        if (size == 0) {
            cash.setMessage("Add one less item");
            cash.setError(true);
            errorData.setValue(cash);
        }
    }

    //check if user add client auto from firebase or not
    @Override
    public void clientNameOrID(String clientName, String clientNameByID) {
        if (!clientName.equals(clientNameByID)) {
            cash.setMessage("add client manual");
            cash.setError(true);
        } else {
            cash.setMessage("add client use id");
            cash.setError(false);
        }
        errorData.setValue(cash);
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }
}
