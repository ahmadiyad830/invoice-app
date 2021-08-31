package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Receipt;

public class RepoReceipt extends Repo<Receipt>{
    public RepoReceipt(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postReceipt(Receipt model) {
        model.setReceiptId(getReference().push().getKey());
        getReference().child(RECEIPT).child(getfUser().getUid()).child(model.getReceiptId())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        getCash().setError(false);
                        getCash().setMessage("success");
                        getCash().setCode(SUCCESS);
                        getErrorDate().setValue(getCash());
                    }

                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    getCash().setError(true);
                    getCash().setMessage(e.getMessage());
                    getCash().setCode(TRY_AGAIN);
                    getErrorDate().setValue(getCash());
                });
        return getErrorDate();
    }

    public LiveData<Cash> postClientTLow(Receipt model) {
        model.setClientId(getReference().push().getKey());
        getReference().child(RECEIPT).child(getfUser().getUid()).push()
                .setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getCash().setError(false);
                        getCash().setMessage("success");
                        getCash().setCode(SUCCESS);
                        getErrorDate().setValue(getCash());
                    }

                })
                .addOnFailureListener(e -> {
                    getCash().setError(true);
                    getCash().setMessage(e.getMessage());
                    getCash().setCode(TRY_AGAIN);
                    getErrorDate().setValue(getCash());
                });
        return getErrorDate();
    }

    public LiveData<Cash> putReceipt(Receipt model){
        return getErrorDate();
    }
    public LiveData<Cash> putReceiptTLow(Receipt model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteReceipt(Receipt model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteReceiptTLow(Receipt model){
        return getErrorDate();
    }
    public LiveData<Receipt> getClient(){
        return getData();
    }
}
