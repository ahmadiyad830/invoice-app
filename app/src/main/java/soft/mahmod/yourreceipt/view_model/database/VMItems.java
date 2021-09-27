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
    private RepoItem repo;

    public VMItems(@NonNull Application application) {
        super(application);
        repo = new RepoItem(application);
    }


    public LiveData<Cash> postItem(Items model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            return repo.postItem(model);
        else return repo.postItemTLow(model);
    }
    public LiveData<Cash> putItem(Items model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            return repo.putItem(model);
        else return repo.putItemTLow(model);
    }
    public LiveData<Cash> updatesQuantity(List<String> ids,List<Double> itemQuantity,List<Double> quantity){
        return repo.updatsQuantity(ids, itemQuantity,quantity);
    }

    public LiveData<Cash> updatesQuantity(String id,double quantity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repo.putQuantity(id, quantity);
        }else {
            return repo.putQuantityTLow(id, quantity);
        }
    }
}
