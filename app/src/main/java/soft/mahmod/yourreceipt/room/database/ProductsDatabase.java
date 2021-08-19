package soft.mahmod.yourreceipt.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import soft.mahmod.yourreceipt.dao.DaoProducts;
import soft.mahmod.yourreceipt.model.entity.EntityProducts;

@Database(entities = EntityProducts.class, version = 8)
public abstract class ProductsDatabase extends RoomDatabase {
    private static ProductsDatabase instance;

    public abstract DaoProducts daoProducts();
    public static synchronized ProductsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductsDatabase.class, "products_save")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
