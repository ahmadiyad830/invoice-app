package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.repository.database.RepoClient;

public class VMClient extends AndroidViewModel {
    private RepoClient repo;

    public VMClient(@NonNull Application application) {
        super(application);
        repo = new RepoClient(application);
    }

    public LiveData<Client> postClient(Client model){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repo.postClient(model);
        }else {
            return repo.postClientTLow(model);
        }
    }
    public LiveData<Client> putClient(Client model){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repo.putClient(model);
        }else {
            return repo.putClientTLow(model);
        }
    }
    public LiveData<Client> getClient(String pushKey){
        return repo.getClient(pushKey);
    }
}
