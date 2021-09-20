package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.repository.database.RepoItem;

public class VMItems extends AndroidViewModel {
    private RepoItem repoItem;

    public VMItems(@NonNull Application application) {
        super(application);
        repoItem = new RepoItem(application);
    }


    public LiveData<Cash> postItem(Items model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            return repoItem.postItem(model);
        else return repoItem.postItemTLow(model);
    }
    public LiveData<Cash> putItem(Items model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            return repoItem.putItem(model);
        else return repoItem.putItemTLow(model);
    }
    public LiveData<Cash> updatesQuantity(List<String> ids,List<Double> itemQuantity,List<Double> quantity){
        return repoItem.updatsQuantity(ids, itemQuantity,quantity);
    }
}
