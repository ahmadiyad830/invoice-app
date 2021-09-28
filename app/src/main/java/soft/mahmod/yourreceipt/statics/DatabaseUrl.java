package soft.mahmod.yourreceipt.statics;

public interface DatabaseUrl extends StateCode {
    // FIXME: 8/31/2021 maybe error because we add / after string path
    String ITEMS = "items/";
    String CLIENT = "client/";
    String STORE = "store/";
    String ORDER = "orders/";
    String RECEIPT = "receipt/";
    String USER = "users/";
    String PASSWORD = "password/";
    String PRODUCTS = "products/";
    String QUANTITY = "/quantity";
    String ERROR = "/error";
    String SECURITY = "/security";
//    paymnet
    String PAYMENT = "payment/";
    String LIST_PAYMENT = "listPayment/";
    String TYPE_PAYMENT = "typePayment/";
    String PAID = "paid/";

    String ACTIVE_USER = "users/active";
    String ACTIVE = "/active";


}
