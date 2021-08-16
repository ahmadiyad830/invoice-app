package soft.mahmod.yourreceipt.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import soft.mahmod.yourreceipt.model.Products;

@Dao
public interface DaoProducts {
    @Insert
    Completable insert(Products model);

    @Query("SELECT * FROM products")
    Single<List<Products>> getProducts();

    @Query("DELETE FROM products")
    Completable deleteAll();

    @Query("DELETE FROM products WHERE productId = :id")
    Completable deleteById(String id);
}
