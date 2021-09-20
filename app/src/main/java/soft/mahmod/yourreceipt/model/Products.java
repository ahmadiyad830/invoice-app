package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.firebase.database.Exclude;

import java.util.List;

public class Products extends Cash {
    private String productId;
    private String itemId;
    @Exclude
    private double itemQuantity;
    private double price;

    private double quantity;

    private double total;

    private String notes;


    private String name;
    private double discount;
    //    TODO insert default value
    private double tax;
    @Ignore
    private List<String> receiptId;
    @Exclude
    private boolean taxClientNoReg;
    @Exclude
    public boolean isTaxClientNoReg() {
        return taxClientNoReg;
    }
    @Exclude
    public void setTaxClientNoReg(boolean taxClientNoReg) {
        this.taxClientNoReg = taxClientNoReg;
    }

    public Products() {

    }

    public Products(double price, double quantity, double total, double discount, double tax) {
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.discount = discount;
        this.tax = tax;
    }
    @Exclude
    public double getItemQuantity() {
        return itemQuantity;
    }
    @Exclude
    public void setItemQuantity(double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", productsPrice=" + price +
                ", productsQuantity=" + quantity +
                ", total=" + total +
                ", notes='" + notes + '\'' +
                ", itemName='" + name + '\'' +
                ", discount=" + discount +
                ", tax=" + tax +
                ", receiptId=" + receiptId +
                ", taxClientNoReg=" + taxClientNoReg +
                '}';
    }
}
