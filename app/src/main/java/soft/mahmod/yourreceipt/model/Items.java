package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Items implements Serializable {
    private String name;
    private double price;
    private double discount;
    private double tax;
    private String note;
    private String itemId;
    private double quantity;
    private List<String> productId;

    public Items() {

    }

    public Items(String name, double price, double discount,
                 double tax, String note
            , double quantity) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.tax = tax;
        this.note = note;
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
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
                "itemName='" + name + '\'' +
                ", itemPrice='" + price + '\'' +
                ", discount='" + discount + '\'' +
                ", itemTax='" + tax + '\'' +
                ", itemNote='" + note + '\'' +
                ", itemId='" + itemId + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
