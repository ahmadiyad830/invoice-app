package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Items implements Serializable {
    private String itemName ;
    private double itemPrice;
    private double discount;
    private double itemTax;
    private String itemNote;
    private long itemId;
    private double quantity;
    private List<String> productId;

    public Items() {

    }

    public Items(String itemName, double itemPrice, double discount, double itemTax, String itemNote, double quantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discount = discount;
        this.itemTax = itemTax;
        this.itemNote = itemNote;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getItemTax() {
        return itemTax;
    }

    public void setItemTax(double itemTax) {
        this.itemTax = itemTax;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public List<String> getProductId() {
        return productId;
    }

    public void setProductId(List<String> productId) {
        this.productId = productId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Items{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", discount='" + discount + '\'' +
                ", itemTax='" + itemTax + '\'' +
                ", itemNote='" + itemNote + '\'' +
                ", itemId='" + itemId + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
