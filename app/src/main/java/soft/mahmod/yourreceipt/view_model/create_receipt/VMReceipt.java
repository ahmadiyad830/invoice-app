package soft.mahmod.yourreceipt.view_model.create_receipt;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.repository.create.RepoReceipt;

public class VMReceipt extends ViewModel {
    private MutableLiveData<Receipt> data;
    private MutableLiveData<Cash> errorData;
    private RepoReceipt repoReceipt;
    public VMReceipt() {
        repoReceipt = new RepoReceipt();
        data = repoReceipt.getData();
        errorData = repoReceipt.getErrorData();
    }
    public void setReceipt(Receipt model){
        repoReceipt.setReceipt(model);
    }
    public MutableLiveData<Receipt> getData() {
        return data;
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }
}
