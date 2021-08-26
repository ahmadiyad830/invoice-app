package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Items implements Serializable {
    private String itemName ;
    private String itemPrice;
    private String discount;
    private String itemTax;
    private String itemNote;
    private String itemId;
    private String quantity;
    private List<String> productId;

    public Items() {

    }

    public Items(String itemName, String itemPrice, String discount,
                 String itemTax, String itemNote,String quantity) {
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

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getItemTax() {
        return itemTax;
    }

    public void setItemTax(String itemTax) {
        this.itemTax = itemTax;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
