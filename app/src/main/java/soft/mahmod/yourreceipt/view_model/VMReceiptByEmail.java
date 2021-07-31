package soft.mahmod.yourreceipt.view_model;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.repository.RepoReceiptByEmail;
import soft.mahmod.yourreceipt.update.data.RefreshLiveData;

public class VMReceiptByEmail extends ViewModel {
    private RepoReceiptByEmail repoReceipt;
    private RefreshLiveData<List<Receipt>> refreshLiveData;

    public VMReceiptByEmail() {
        repoReceipt = new RepoReceiptByEmail();

    }
    public LiveData<List<Receipt>> receiptByEmail(String email){
        return repoReceipt.getReceiptByEmail(email);
    }

}
