package soft.mahmod.yourreceipt.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Products extends Cash {
    private int productId;

    private String productsPrice;

    private String productsQuantity;

    private String total;

    private String notes;


    private String itemName;
    private String discount;
    //    TODO insert default value
    private String tax;
    @Ignore
    private List<String> receiptId;
    //constructor retrofit

    public Products(String message, Boolean error, Integer code,
                    int productId, @Nullable String productsPrice, @Nullable String productsQuantity,
                    @Nullable String total, @Nullable String notes,  @Nullable String itemName) {
        super(message, error, code);
        this.itemName = itemName;
        this.productId = productId;
        this.productsPrice = productsPrice;
        this.productsQuantity = productsQuantity;
        this.total = total;
        this.notes = notes;

    }

    //constructor room database
    public Products(int productId, @Nullable String productsPrice, @Nullable String productsQuantity,
                    @Nullable String total, @Nullable String notes,  @Nullable String itemName) {
        this.productId = productId;
        this.productsPrice = productsPrice;
        this.productsQuantity = productsQuantity;
        this.total = total;
        this.itemName = itemName;
        this.notes = notes;


    }

    // default constructor
    public Products() {

    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public @Nullable String getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(@Nullable String productsPrice) {
        this.productsPrice = productsPrice;
    }

    public @Nullable String getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(@Nullable String productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public @Nullable String getTotal() {
        return total;
    }

    public void setTotal(@Nullable String total) {
        this.total = total;
    }

    public @Nullable String getNotes() {
        return notes;
    }

    public void setNotes(@Nullable String notes) {
        this.notes = notes;
    }



    public @Nullable String getItemName() {
        return itemName;
    }

    public void setItemName(@Nullable String itemName) {
        this.itemName = itemName;
    }


    @NonNull
    @Override
    public String toString() {
        return "Products{" +
                "productId='" + productId + '\'' +
                ", productsPrice='" + productsPrice + '\'' +
                ", productsQuantity='" + productsQuantity + '\'' +
                ", total='" + total + '\'' +
                ", notes='" + notes + '\'' +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
