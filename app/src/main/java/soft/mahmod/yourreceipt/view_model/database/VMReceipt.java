package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.repository.database.RepoReceipt;

public class VMReceipt extends AndroidViewModel {

    private RepoReceipt repo;

    public VMReceipt(@NonNull Application application) {
        super(application);
        repo = new RepoReceipt(application);
    }

    public LiveData<Receipt> postReceipt(Receipt model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repo.postReceipt(model);
        } else return repo.postClientTLow(model);
    }

    public LiveData<Receipt> getReceipt(String pushKey) {
        return repo.getReceipt(pushKey);
    }

    public LiveData<Receipt> editValue(double editValue, String pushKey, String keyEdit) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ? repo.putEditValue(editValue, pushKey, keyEdit)
                : repo.putEditValueTLow(editValue, pushKey, keyEdit);
    }
    public LiveData<Integer> sizeReference(String ref1){
        return repo.sizeReference(ref1);
    }


}
