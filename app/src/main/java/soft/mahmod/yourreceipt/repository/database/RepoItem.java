package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.stream.Collectors;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Items;

public class RepoItem extends Repo<Items> implements OnCompleteListener<Void>, OnFailureListener {
    private Items items = new Items();
    public RepoItem(Application application) {
        super(application);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Items> postItem(Items model) {
        model.setItemId(getReference().push().getKey());
        getReference().child(ITEMS).child(getfUser().getUid()).child(model.getItemId())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(), this)
                .addOnFailureListener(getApplication().getMainExecutor(),this);
        return getData();
    }
    public LiveData<Items> postItemTLow(Items model) {
        model.setItemId(getReference().push().getKey());
        getReference().child(ITEMS).child(getfUser().getUid()).child(model.getItemId())
                .setValue(model)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Items> putItem(Items model){
        getReference()
                .child(ITEMS)
                .child(getfUser().getUid())
                .child(model.getItemId())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(), this)
                .addOnFailureListener(getApplication().getMainExecutor(),this);
        return getData();
    }
    public LiveData<Items> putItemTLow(Items model){
        getReference().child(ITEMS).child(getfUser().getUid()).child(model.getItemId())
                .setValue(model)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Items> putQuantity(String id, double quantity) {
        try {
            getReference()
                    .child(ITEMS)
                    .child(getfUser().getUid())
                    .child(id)
                    .child(QUANTITY)
                    .setValue(quantity)
                    .addOnCompleteListener(getApplication().getMainExecutor(), this)
                    .addOnFailureListener(getApplication().getMainExecutor(),this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return getData();
    }

    public LiveData<Items> putQuantityTLow(String id, double quantity) {
        try {
            getReference().child(ITEMS).child(getfUser().getUid()).child(id)
                    .child(QUANTITY)
                    .setValue(quantity)
                    .addOnCompleteListener(this)
                    .addOnFailureListener(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return getData();
    }

    public LiveData<Items> updatsQuantity(List<String> ids, List<Double> itemQuantity, List<Double> quantity) {
        LiveData<Items> liveData = new MutableLiveData<>();
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


    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Items> deleteItem(String itemId) {
        getReference().child(ITEMS)
                .child(getfUser().getUid())
                .child(itemId)
                .removeValue()
                .addOnCompleteListener(getApplication().getMainExecutor(),this)
                .addOnFailureListener(getApplication().getMainExecutor(),this);
        return getData();
    }

    public LiveData<Items> deleteItemTLow(String itemId) {
        getReference().child(ITEMS)
                .child(getfUser().getUid())
                .child(itemId)
                .removeValue()
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
    }

    public LiveData<Items> getItems() {
        return getData();
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            items.setError(false);
            items.setMessage("success");
            items.setCode(SUCCESS);
            getData().setValue(items);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        postError(e.getLocalizedMessage());
        items.setError(true);
        items.setMessage(e.getMessage());
        items.setCode(TRY_AGAIN);
        getData().setValue(items);
    }
}
