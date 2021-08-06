package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.repository.RepoReceiptByClientName;

public class VMReceiptByClientName extends ViewModel {
    private RepoReceiptByClientName repoReceipt;

    public VMReceiptByClientName() {
        repoReceipt = new RepoReceiptByClientName();
    }

    public LiveData<List<Receipt>> receiptByClientName(String clientName) {
        return repoReceipt.receiptByClientName("%"+clientName+"%");
    }
}
