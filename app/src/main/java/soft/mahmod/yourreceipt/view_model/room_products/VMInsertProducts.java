package soft.mahmod.yourreceipt.view_model.room_products;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.Completable;
import soft.mahmod.yourreceipt.model.entity.EntityProducts;
import soft.mahmod.yourreceipt.room.database.ProductsDatabase;

public class VMInsertProducts extends AndroidViewModel {
    private ProductsDatabase database;

    public VMInsertProducts(@NonNull Application application) {
        super(application);
        database = ProductsDatabase.getInstance(application);
    }

    public Completable insertProduct(EntityProducts model) {
        return database.daoProducts().insert(model);
    }
}
