package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Products;

public class RepoProducts extends Repo<Products>{
    public RepoProducts(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postProducts(Products model) {
        model.setProductId(getReference().push().getKey());
        getReference().child(PRODUCTS).child(getfUser().getUid()).child(model.getProductId())
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
    public LiveData<Cash> postProductsTLow(Products model) {
        model.setProductId(getReference().push().getKey());
        getReference().child(PRODUCTS).child(getfUser().getUid()).child(model.getProductId())
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
    public LiveData<Cash> putProducts(Products model){
        return getErrorDate();
    }
    public LiveData<Cash> putProductsTLow(Products model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteProducts(Products model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteProductsTLow(Products model){
        return getErrorDate();
    }
    public LiveData<Products> getProducts(){
        return getData();
    }
}
