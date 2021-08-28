package soft.mahmod.yourreceipt.view_model.database;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Store;
import soft.mahmod.yourreceipt.repository.Database.RepoStore;

public class VMStore extends ViewModel {
    private RepoStore repoStore;
    private MutableLiveData<Cash> errorDate;
    private MutableLiveData<Store> data;
    public VMStore() {
        repoStore = new RepoStore();
        errorDate = repoStore.getErrorData();
        data = repoStore.getData();
    }

    public void insertStore(Store model) {
        repoStore.insertStore(model);
    }

    public void readStore() {
        repoStore.readObject();
    }

    public MutableLiveData<Cash> getErrorDate() {
        return errorDate;
    }

    public MutableLiveData<Store> getData() {
        return data;
    }
}
