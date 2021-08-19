package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Items extends User {
    @SerializedName("item_name")
    private String itemName;
    @SerializedName("item_price")
    private String itemPrice;
    @SerializedName("discount")
    private String discount;
    @SerializedName("item_tax")
    private String itemTax;
    @SerializedName("item_note")
    private String itemNote;
    @SerializedName("item_id")
    private String itemId;
    @SerializedName("quantity")
    private String quantity;



    public Items(String message, boolean error, Integer code, String email) {
        super(message, error, code);
        setEmail(email);
    }


    public Items(String userId, String itemName, String itemPrice,
                 String itemTax, String itemNote,  String quantity) {
        super(userId);
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemTax = itemTax;
        this.itemNote = itemNote;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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
                '}'+super.toString();
    }
}
