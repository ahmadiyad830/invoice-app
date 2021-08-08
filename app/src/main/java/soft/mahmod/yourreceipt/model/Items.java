package soft.mahmod.yourreceipt.model;

import com.google.gson.annotations.SerializedName;

public class Items extends User{
    @SerializedName("item_name")
    String itemName;
    @SerializedName("item_price")
    String itemPrice;
    @SerializedName("discount")
    String discount;
    @SerializedName("item_tax")
    String itemTax;
    @SerializedName("item_note")
    String itemNote;
    @SerializedName("item_id")
    String itemId;

    public Items(String message, boolean error, Integer code,String email) {
        super(message, error, code);
        setEmail(email);
    }


    public Items(String userId, String itemName, String itemPrice, String discount, String itemTax, String itemNote) {
        super(userId);
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discount = discount;
        this.itemTax = itemTax;
        this.itemNote = itemNote;
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
}
