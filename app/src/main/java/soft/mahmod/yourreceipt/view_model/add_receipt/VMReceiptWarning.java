package soft.mahmod.yourreceipt.view_model.add_receipt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import soft.mahmod.yourreceipt.conditions.catch_receipt.RepoReceiptWarning;
import soft.mahmod.yourreceipt.model.Receipt;

public class VMReceiptWarning extends ViewModel {
    private RepoReceiptWarning repoReceiptWarning;

    public VMReceiptWarning() {
        repoReceiptWarning = new RepoReceiptWarning();
    }

    public LiveData<Map<String, String>> getWarning() {
        return repoReceiptWarning.getDataWarning();
    }


    public void setListWarning(Map<String, String> warnings) {
        repoReceiptWarning.setListWarning(warnings);
    }
}
