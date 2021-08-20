package soft.mahmod.yourreceipt.model.entity;

import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Nullable;

import soft.mahmod.yourreceipt.model.Products;

@Entity(tableName = "EntityProducts")
public class EntityProducts extends Products {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public EntityProducts() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEmpty(String object) {
        if (object != null) {
            return TextUtils.isEmpty(object);
        }
        return true;
    }

    public void setTotalProducts(double price, double quantity, double discount, double tax) {
        super.setTotal(String.valueOf(getPriceProduct(price,discount,tax)*quantity));
    }

    public double getPriceProduct(double price, double discount, double tax) {
        double prd = price - discount;
        return prd - tax;
    }

    public void setDiscount(double price, double discount) {
        super.setDiscount(String.valueOf(price - discount));
    }

    public void setTax(double tax, double price) {
        super.setTax(String.valueOf(price * tax / 100));
    }
}
