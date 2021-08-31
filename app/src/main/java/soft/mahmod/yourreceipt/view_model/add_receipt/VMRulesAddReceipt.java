package soft.mahmod.yourreceipt.view_model.add_receipt;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.conditions.catch_add_receipt.RulesAddReceipt;
import soft.mahmod.yourreceipt.model.Cash;

public class VMRulesAddReceipt extends ViewModel {
    private MutableLiveData<Cash> errorData;
    private RulesAddReceipt rulesAddReceipt;

    public VMRulesAddReceipt() {
        rulesAddReceipt = new RulesAddReceipt();
        errorData = rulesAddReceipt.getErrorData();
    }
    public void sizeListProduct(int size){
        rulesAddReceipt.sizeListProducts(size);
    }
    public void clientNameOrID(String clientName,String clientNameByID){
        rulesAddReceipt.clientNameOrID(clientName, clientNameByID);
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }
}
