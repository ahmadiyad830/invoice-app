package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Store;

public class RepoStore extends Repo<Store> {
    public RepoStore(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postStore(Store model) {
        getReference().child(STORE).child(getfUser().getUid())
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

    public LiveData<Cash> postStoreTLow(Store model) {
        getReference().child(STORE).child(getfUser().getUid())
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

    public LiveData<Cash> putStore(Store model) {
        return getErrorDate();
    }

    public LiveData<Cash> putStoreTLow(Store model) {
        return getErrorDate();
    }

    public LiveData<Cash> deleteStore(Store model) {
        return getErrorDate();
    }

    public LiveData<Cash> deleteStoreTLow(Store model) {
        return getErrorDate();
    }

    public LiveData<Store> getStore() {
        setT(new Store());
       getReference().child(STORE).child(getfUser().getUid())
               .addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.exists()){
                           getT().setError(false);
                           getT().setMessage("success");
                           getData().setValue(snapshot.getValue(Store.class));
                       }else {
                           getT().setError(true);
                           getT().setMessage("path not exists");
                           getT().setCode(404);
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       getT().setMessage(error.getMessage());
                       getT().setError(true);
                       getData().setValue(getT());
                   }
               });
        return getData();
    }
}
