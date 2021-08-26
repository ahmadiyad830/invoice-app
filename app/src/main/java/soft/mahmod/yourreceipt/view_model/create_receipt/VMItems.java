package soft.mahmod.yourreceipt.view_model.create_receipt;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.repository.create.RepoItem;

public class VMItems extends ViewModel {
    private RepoItem repoItem;
    private MutableLiveData<Items> data;
    private MutableLiveData<Cash> errorData;

    public VMItems() {
        repoItem = new RepoItem();
        data = repoItem.getData();
        errorData = repoItem.getErrorData();
    }

    public void createItem(Items model) {
        repoItem.createItem(model);
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }

    public MutableLiveData<Items> getData() {
        return data;
    }
}
