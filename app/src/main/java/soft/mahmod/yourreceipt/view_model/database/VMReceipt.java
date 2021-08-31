package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.repository.database.RepoReceipt;

public class VMReceipt extends AndroidViewModel {

    private RepoReceipt repoReceipt;

    public VMReceipt(@NonNull Application application) {
        super(application);
        repoReceipt = new RepoReceipt(application);
    }

    public LiveData<Cash> postReceipt(Receipt model){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repoReceipt.postReceipt(model);
        }else return repoReceipt.postClientTLow(model);
    }

}
