package soft.mahmod.yourreceipt.statics;


public interface ApiURLS {
    //    TODO STATIC URLS{@prams }
    /*
    * {@Pram}
    * */
//
//    http://192.168.1.73/back%20end/api/
    String STATIC_URL = " http://192.168.1.73/back%20end/api/";
    //    TODO USER ACCOUNT
    String SIGN_IN = "query_registration/sign_in.php";
    String SIGN_UP = "query_registration/sign_up.php";
    String CHANGE_PASSWORD = "query_account_user/change_password.php";
    //    TODO QUERY RECEIPT
    String ALL_RECEIPT_BY_EMAIL = "query_receipt/all_receipt_user.php";
    String ALL_RECEIPT_BY_CLIENT_NAME = "query_receipt/receipt_by_client.php";
    String CREATE_RECEIPT = "query_receipt/create_receipt.php";
    String PRODUCTS_BY_RECEIPT_ID = "query_products/products_by_id.php";
    String ADD_LOGO = "query_account_user/add_logo.php";
    //    TODO ITEMS QUERY
    String ITEM_BY_EMAIL = "query_item/items_by_email.php";
    String CREATE_ITEM = "query_item/item_create.php";
    String ITEM_DELETE = "query_item/item_delete.php";
    String ITEM_UPDATE = "query_item/item_update.php";
    //    first request after registration
    String USER_CONN = "query_connection/query_conn.php";
    //    create client
    String CREATE_CLIENT = "query_client/client_create.php";
    String ALL_CLIENT_BY_EMAIL = "query_client/client_by_email.php";

    String INSERT_PRODUCTS = "query_products/insert_products.php";

}
