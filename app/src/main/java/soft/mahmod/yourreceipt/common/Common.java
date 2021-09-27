package soft.mahmod.yourreceipt.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import soft.mahmod.yourreceipt.model.Products;

public class Common {
    private static final String TAG = "Common";
    public final static List<Products> listProduct = new ArrayList<>();
    private static double totalAll = 0.0;
    public static double quantity = 0.0;

    public static double getTotalAll() {
        String formatTotal = String.format(Locale.getDefault(),"%.2f",Common.totalAll);
        return Double.parseDouble(formatTotal);
    }

    public static void setTotalAll(double totalAll) {
        String formatTotal = String.format(Locale.getDefault(),"%.2f",totalAll);
        Common.totalAll = Double.parseDouble(formatTotal);
    }
}
