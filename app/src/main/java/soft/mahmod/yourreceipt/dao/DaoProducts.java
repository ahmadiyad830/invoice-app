package soft.mahmod.yourreceipt.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import soft.mahmod.yourreceipt.model.entity.EntityProducts;

@Dao
public interface DaoProducts {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(EntityProducts model);

    @Query("SELECT * FROM EntityProducts")
    Single<List<EntityProducts>> getEntityProducts();

    @Query("DELETE FROM EntityProducts")
    Completable deleteAll();

    @Query("DELETE FROM EntityProducts WHERE id = :id")
    Completable deleteById(String id);
}
