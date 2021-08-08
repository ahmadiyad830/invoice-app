package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.repository.RepoCreateItem;

public class VMCreateItem extends ViewModel {
    private RepoCreateItem repoCreateItem;

    public VMCreateItem() {
        repoCreateItem = new RepoCreateItem();
    }
    public LiveData<Cash> createItem(Items model){
        return repoCreateItem.createItem(model);
    }
}
