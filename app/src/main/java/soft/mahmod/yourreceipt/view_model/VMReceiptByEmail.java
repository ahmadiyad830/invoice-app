package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.RepoReceiptByEmail;
import soft.mahmod.yourreceipt.response.ResponseReceipt;

public class VMReceiptByEmail extends ViewModel {
    private RepoReceiptByEmail repoReceipt;

    public VMReceiptByEmail() {
        repoReceipt = new RepoReceiptByEmail();
    }
    public LiveData<List<Receipt>> receiptByEmail(String email){
        return repoReceipt.getReceiptByEmail(email);
    }
}
