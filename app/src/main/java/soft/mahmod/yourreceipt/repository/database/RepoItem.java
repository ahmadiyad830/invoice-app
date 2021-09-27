package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

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
    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> putItem(Items model){
        getReference()
                .child(ITEMS)
                .child(getfUser().getUid())
                .child(model.getItemId())
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
    public LiveData<Cash> putItemTLow(Items model){
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

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> putQuantity(String id, double quantity) {
        getReference()
                .child(ITEMS)
                .child(getfUser().getUid())
                .child(id)
                .child(QUANTITY)
                .setValue(quantity)
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

    public LiveData<Cash> putQuantityTLow(String id, double quantity) {
        getReference().child(ITEMS).child(getfUser().getUid()).child(id)
                .child(QUANTITY)
                .setValue(quantity)
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

    public LiveData<Cash> updatsQuantity(List<String> ids, List<Double> itemQuantity, List<Double> quantity) {
        LiveData<Cash> liveData = new MutableLiveData<>();
        for (int i = 0; i < ids.size(); i++) {
            double qu = itemQuantity.get(i) - quantity.get(i);
            if (qu <= 0)
                qu = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                liveData = putQuantity(ids.get(i), qu);
            } else {
                liveData = putQuantityTLow(ids.get(i), qu);
            }
        }
        return liveData;
    }


    public LiveData<Cash> deleteItem(Items model) {
        return getErrorDate();
    }

    public LiveData<Cash> deleteItemTLow(Items model) {
        return getErrorDate();
    }

    public LiveData<Items> getItems() {
        return getData();
    }
}
