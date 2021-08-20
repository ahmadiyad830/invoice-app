package soft.mahmod.yourreceipt.view_model.room_products;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import soft.mahmod.yourreceipt.model.entity.EntityProducts;
import soft.mahmod.yourreceipt.room.database.ProductsDatabase;

public class VMProducts extends AndroidViewModel {
    private Runnable runnable;
    private Handler handler = new Handler();
    private ProductsDatabase database;
    private CompositeDisposable disposable = new CompositeDisposable();

    public VMProducts(@NonNull Application application) {
        super(application);
        database = ProductsDatabase.getInstance(application);
    }

    public Single<List<EntityProducts>> getProducts() {
        return database.daoProducts().getEntityProducts();
    }

    public Completable deleteById(EntityProducts model) {
        return database.daoProducts().deleteById(model);
    }

    public Completable deleteAll() {
        return database.daoProducts().deleteAll();
    }

    public Single<List<String>> totalAll() {
        return database.daoProducts().totalAll();
    }

    @Override
    protected void onCleared() {
        handler.removeCallbacks(runnable);
        disposable.clear();
        super.onCleared();
    }
    public void clear(){
        onCleared();
    }
}
