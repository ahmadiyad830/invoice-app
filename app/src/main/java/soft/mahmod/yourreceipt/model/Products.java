package soft.mahmod.yourreceipt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Products extends Cash implements Serializable {
    @SerializedName("products_id")
    private String productId;
    @SerializedName("products_price")
    private String productsPrice;
    @SerializedName("product_quantity")
    private String productsQuantity;
    @SerializedName("total")
    private String total;
    @SerializedName("notes")
    private String notes;
    @SerializedName("receipt_id")
    private String receiptId;
    @SerializedName("item_name")
    private String itemName;

    public Products(String message, Boolean error, Integer code,
                    String productId, String productsPrice, String productsQuantity,
                    String total, String notes, String receiptId,String itemName) {
        super(message, error, code);
        this.itemName = itemName;
        this.productId = productId;
        this.productsPrice = productsPrice;
        this.productsQuantity = productsQuantity;
        this.total = total;
        this.notes = notes;
        this.receiptId = receiptId;
    }

    public Products(String productId, String productsPrice, String productsQuantity,
                    String total, String notes, String receiptId,String itemName) {
        this.productId = productId;
        this.productsPrice = productsPrice;
        this.productsQuantity = productsQuantity;
        this.total = total;
        this.itemName = itemName;
        this.notes = notes;
        this.receiptId = receiptId;
    }

    public Products() {

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(String productsPrice) {
        this.productsPrice = productsPrice;
    }

    public String getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(String productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

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
