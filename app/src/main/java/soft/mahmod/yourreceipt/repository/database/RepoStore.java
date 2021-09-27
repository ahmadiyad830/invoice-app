package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.R;
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
                    postError(e.getLocalizedMessage());
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
                    postError(e.getLocalizedMessage());
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
                       Store store = new Store();
                       if (snapshot.exists()) {
                           store = snapshot.getValue(Store.class);
                           store.setError(false);
                           if (store.getSecurity() != null && store.getSecurity().isEmpty()) {
                               store.setCode(SUCCESS);
                               store.setMessage("success");
                           } else {
                               store.setCode(NUMBER_SECURITY);
                               store.setMessage(getApplication().getResources().getString(R.string.dont_have_number_security));
                           }
                       }else {
                           store.setError(true);
                           store.setMessage("path not exists");
                           store.setCode(NOT_FOUND);
                       }
                       getData().setValue(store);

                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       postError(error.getMessage());
                       getT().setMessage(error.getMessage());
                       getT().setError(true);
                       getT().setCode(TRY_AGAIN);
                       getData().setValue(getT());
                   }
               });
        return getData();
    }

}
