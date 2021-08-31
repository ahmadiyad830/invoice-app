package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Items;

public class RepoItem extends Repo<Items> {
    public RepoItem(Application application) {
        super(application);
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postItem(Items model) {
        model.setItemId(getReference().push().getKey());
        getReference().child(ITEMS).child(getfUser().getUid()).child(model.getItemId())
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
    public LiveData<Cash> postItemTLow(Items model) {
        model.setItemId(getReference().push().getKey());
        getReference().child(ITEMS).child(getfUser().getUid()).child(model.getItemId())
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
    public LiveData<Cash> putItems(Items model){
        return getErrorDate();
    }
    public LiveData<Cash> putItemsTLow(Items model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteItems(Items model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteItemsTLow(Items model){
        return getErrorDate();
    }
    public LiveData<Items> getItems(){
        return getData();
    }
}
