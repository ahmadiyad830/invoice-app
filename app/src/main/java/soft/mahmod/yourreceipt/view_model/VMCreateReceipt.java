package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.repository.RepoCreateReceipt;

public class VMCreateReceipt extends ViewModel {
    private RepoCreateReceipt receipt;

    public VMCreateReceipt() {
        receipt = new RepoCreateReceipt();
    }
    public LiveData<Cash> createReceipt(Receipt model){
        return receipt.createReceipt(model);
    }
}
