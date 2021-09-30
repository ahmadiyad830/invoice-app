package soft.mahmod.yourreceipt.helper;

import android.util.Log;

import soft.mahmod.yourreceipt.model.Products;

public class CalculatorTax {
    private static final String TAG = "CalculatorTax";

    public static double withTax(Products model) {
        double price = model.getPrice() - model.getDiscount();
        double tax = model.getTax();
        double total = (price * tax) + price;
        return total * model.getQuantity();
    }

    public static double withoutTax(Products model) {
        double price = model.getPrice() - model.getDiscount();
        return price * model.getQuantity();
    }
//     String sim = "( " + price * tax + " )" + "+" + price + "=" + total;
}
