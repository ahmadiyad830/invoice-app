package soft.mahmod.yourreceipt.common;

import java.util.ArrayList;
import java.util.List;

import soft.mahmod.yourreceipt.model.Products;

public class Common {
    private static final String TAG = "Common";
    public final static List<Products> listProduct = new ArrayList<>();
    private static double totalAll = 0.0;
    public static double quantity = 0.0;

    public static double getTotalAll() {
        return totalAll;
    }

    public static void setTotalAll(double totalAll) {
        Common.totalAll = totalAll;
    }
}
