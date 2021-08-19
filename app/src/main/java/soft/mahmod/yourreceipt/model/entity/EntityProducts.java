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

    public void setTotalProducts(double price, double quantity) {
        double discount = Double.parseDouble(getDiscount());
        double tax = Double.parseDouble(getTax());
        if (discount > 0) {
            super.setTotal(String.valueOf((price - discount) * quantity));
        } else if (tax > 1) {
            super.setTotal(String.valueOf((price - tax) * quantity));
        } else if (tax > 0 && discount > 0) {
            super.setTotal(String.valueOf((price - tax - discount) * quantity));
        } else {
            super.setTotal(String.valueOf(price * quantity));
        }
    }

    public void setDiscount(double price, double discount) {
        super.setDiscount(String.valueOf(price - discount));
    }

    public void setTax(double tax, double price) {
        super.setTax(String.valueOf(price * tax / 100));
    }
}
