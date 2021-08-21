package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.repository.RepoCreateProducts;

public class VMCreateProducts extends ViewModel {
    private RepoCreateProducts repoCreateProducts;

    public VMCreateProducts() {
        repoCreateProducts = new RepoCreateProducts();
    }
    public LiveData<Cash> createProducts(
            int numLoop,
            String[] receiptId,
            String[] price,
            String[] quantity,
            String[] total,
            String[] notes,
            String[] name
    ){
        return repoCreateProducts.createProducts(numLoop, receiptId,price,quantity,total,notes,name);
    }
}
