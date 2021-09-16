package soft.mahmod.yourreceipt.model.billing;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.model.Cash;

public class Payment  extends Cash {
    private String date;
    private double price;
    private List<Payment> listPayment;
    private  String typePayment ;
    public String getDate() {
        return date;
    }
    public Payment() {
    }

    public Payment(List<Payment> listPayment) {
        this.listPayment = listPayment;
    }

    public Payment(String date) {
        this.date = date;
    }

    public String getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(String typePayment) {
        this.typePayment = typePayment;
    }

    public List<Payment> getListPayment() {
        return listPayment;
    }

    public void setListPayment(List<Payment> listPayment) {
        this.listPayment = listPayment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Payment{" +
                "date='" + date + '\'' +
                ", price=" + price +
                ", listPayment=" + listPayment +
                '}';
    }
}
