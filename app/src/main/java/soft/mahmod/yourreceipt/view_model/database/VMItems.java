package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
}
