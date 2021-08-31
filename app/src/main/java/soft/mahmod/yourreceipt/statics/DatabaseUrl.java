package soft.mahmod.yourreceipt.statics;

import com.google.firebase.auth.FirebaseAuth;

public interface DatabaseUrl extends ErrorCode {
    // FIXME: 8/31/2021 maybe error because we add / after string path
    String ITEMS = "items/";
    String CLIENT = "client/";
    String STORE = "store/";
    String ORDER = "orders/";
    String RECEIPT = "receipt/";
    String USER = "users/";
    String PRODUCTS = "products/";

    String ACTIVE_USER = "users/active";
    String ACTIVE = "/active";


}
