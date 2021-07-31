package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.repository.RepoProductsByReceiptId;

public class VMProductsByReceiptId extends ViewModel {
    private RepoProductsByReceiptId repoProductsByReceiptId;

    public VMProductsByReceiptId() {
        repoProductsByReceiptId = new RepoProductsByReceiptId();
    }
    public LiveData<List<Products>> productsByReceiptId(String receiptId){
        return repoProductsByReceiptId.productsByReceiptId(receiptId);
    }
}
