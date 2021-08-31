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
    private String productId;

    private double productsPrice;

    private double productsQuantity;

    private double total;

    private String notes;


    private String itemName;
    private double discount;
    //    TODO insert default value
    private double tax;
    @Ignore
    private List<String> receiptId;

    public Products() {

    }

    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(double productsPrice) {
        this.productsPrice = productsPrice;
    }

    public double getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(double productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public List<String> getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(List<String> receiptId) {
        this.receiptId = receiptId;
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
