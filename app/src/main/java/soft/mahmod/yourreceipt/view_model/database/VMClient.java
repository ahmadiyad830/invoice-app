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
    private RepoClient repoClient;

    public VMClient(@NonNull Application application) {
        super(application);
        repoClient = new RepoClient(application);
    }

    public LiveData<Cash> postClient(Client model){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repoClient.postClient(model);
        }else {
            return repoClient.postClientTLow(model);
        }
    }
    public LiveData<Client> getClient(String pushKey){
        return repoClient.getClient(pushKey);
    }


}
