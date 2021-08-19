package soft.mahmod.yourreceipt.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

public class Products extends Cash {
    @Ignore
    @SerializedName("products_id")
    private int productId;

    @Nullable("1")
    @SerializedName("products_price")
    private String productsPrice;

    @Nullable("1")
    @SerializedName("product_quantity")
    private String productsQuantity;

    @Nullable
    @SerializedName("total")
    private String total;

    @Nullable("N/A")
    @ColumnInfo(defaultValue = "N/A")
    @SerializedName("notes")
    private String notes;

    @Nullable
    @Ignore
    @SerializedName("receipt_id")
    private String receiptId;

    @Nullable
    @ColumnInfo(defaultValue = "N/A")
    @SerializedName("item_name")
    private String itemName;
    @SerializedName("discount")
    private String discount;
    @SerializedName("item_tax")
    private String tax;

    //constructor retrofit
    @Ignore
    public Products(String message, Boolean error, Integer code,
                    int productId, @Nullable String productsPrice, @Nullable String productsQuantity,
                    @Nullable String total, @Nullable String notes, @Nullable String receiptId, @Nullable String itemName) {
        super(message, error, code);
        this.itemName = itemName;
        this.productId = productId;
        this.productsPrice = productsPrice;
        this.productsQuantity = productsQuantity;
        this.total = total;
        this.notes = notes;
        this.receiptId = receiptId;
    }

    //constructor room database
    public Products(int productId, @Nullable String productsPrice, @Nullable String productsQuantity,
                    @Nullable String total, @Nullable String notes, @Nullable String receiptId, @Nullable String itemName) {
        this.productId = productId;
        this.productsPrice = productsPrice;
        this.productsQuantity = productsQuantity;
        this.total = total;
        this.itemName = itemName;
        this.notes = notes;
        this.receiptId = receiptId;

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

    public @Nullable String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(@Nullable String receiptId) {
        this.receiptId = receiptId;
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
                ", receiptId='" + receiptId + '\'' +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
