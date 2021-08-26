package soft.mahmod.yourreceipt.statics;

import com.google.firebase.auth.FirebaseAuth;

public interface DatabaseUrl extends ErrorCode{
    String ALL_RECEIPT = "receipt/";
    String ITEMS = "items/";
    String CLIENT= "client/";
    String USER= "users/";
    String ACTIVE_USER = "users/active";
    String ACTIVE = "/active";

}
