package soft.mahmod.yourreceipt.view_model.room_products;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import io.reactivex.Single;
import soft.mahmod.yourreceipt.model.entity.EntityProducts;
import soft.mahmod.yourreceipt.room.database.ProductsDatabase;

public class VMProducts extends AndroidViewModel {
    private ProductsDatabase database;
    public VMProducts(@NonNull Application application) {
        super(application);
        database = ProductsDatabase.getInstance(application);
    }
    public Single<List<EntityProducts>> getProducts(){
        return database.daoProducts().getEntityProducts();
    }
}
