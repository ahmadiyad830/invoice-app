package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Store;
import soft.mahmod.yourreceipt.repository.database.RepoStore;

public class VMStore extends AndroidViewModel {
    private RepoStore repoStore;

    public VMStore(@NonNull Application application) {
        super(application);
        repoStore = new RepoStore(application);
    }


    public LiveData<Store> postStore(Store model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repoStore.postStore(model);
        } else return repoStore.postStoreTLow(model);
    }
    public LiveData<Store> getStore(){
        return repoStore.getStore();
    }
}
