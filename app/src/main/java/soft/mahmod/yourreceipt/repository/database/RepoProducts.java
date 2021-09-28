package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Products;

public class RepoProducts extends Repo<Products>  implements OnCompleteListener<Void>, OnFailureListener {
    private Products products = new Products();
    public RepoProducts(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Products> postProducts(Products model) {
        model.setProductId(getReference().push().getKey());
        getReference().child(PRODUCTS).child(getfUser().getUid()).child(model.getProductId())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(), this)
                .addOnFailureListener(getApplication().getMainExecutor(), this);
        return getData();
    }
    public LiveData<Products> postProductsTLow(Products model) {
        model.setProductId(getReference().push().getKey());
        getReference().child(PRODUCTS).child(getfUser().getUid()).child(model.getProductId())
                .setValue(model)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
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

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            products.setError(false);
            products.setMessage("success");
            products.setCode(SUCCESS);
            getData().setValue(products);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        postError(e.getLocalizedMessage());
        products.setError(true);
        products.setMessage(e.getMessage());
        products.setCode(TRY_AGAIN);
        getData().setValue(products);
    }
}
