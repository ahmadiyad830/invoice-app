package soft.mahmod.yourreceipt.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.statics.ApiURLS;

public interface ApiServes extends ApiURLS {
    @FormUrlEncoded
    @POST(SIGN_IN)
    Call<User> signIn(@Field("email") String email,
                      @Field("password") String password);

    @FormUrlEncoded
    @POST(SIGN_UP)
    Call<Cash> signUp(@Field("email") String email,
                      @Field("password") String password);

    @FormUrlEncoded
    @POST(ALL_RECEIPT_BY_EMAIL)
    Call<List<Receipt>> getReceiptByEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST(ALL_RECEIPT_BY_CLIENT_NAME)
    Call<List<Receipt>> getReceiptByClient(@Field("client_name") String clientName);

    //    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST(CREATE_RECEIPT)
    Call<Cash> createReceipt(@Field("subject") String subject,
                             @Field("receipt_date") String receiptDate,
                             @Field("client_id") String clientId,
                             @Field("total_all") String totalAll,
                             @Field("client_phone") String clientPhone,
                             @Field("receipt_user_id") String userId);

    @FormUrlEncoded
    @POST(PRODUCTS_BY_RECEIPT_ID)
    Call<List<Products>> productsByReceiptId(
            @Field("receipt_id") String receiptId
    );

    @FormUrlEncoded
    @POST(ADD_LOGO)
    Call<Cash> addLogo(@Field("path") String path, @Field("email") String email);

    @FormUrlEncoded
    @POST(CHANGE_PASSWORD)
    Call<Cash> changePassword(@Field("email") String email,
                              @Field("old_password") String oldPass,
                              @Field("password") String password,
                              @Field("passwordConfirm") String passwordC);

    @FormUrlEncoded
    @POST(ITEM_BY_EMAIL)
    Call<List<Items>> itemsByEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST(CREATE_ITEM)
    Call<Cash> createItem(
            @Field("user_id") String userId,
            @Field("item_name") String itemName,
            @Field("item_price") String itemPrice,
            @Field("discount") String discount,
            @Field("item_tax") String itemTax,
            @Field("item_note") String itemNote
    );

    @FormUrlEncoded
    @POST(USER_CONN)
    Call<Cash> userConnection(@Field("email") String email);


    @FormUrlEncoded
    @POST(CREATE_CLIENT)
    Call<Cash> createClient(
            @Field("client_email") String email,
            @Field("phone_number") String phone,
            @Field("additional_information") String addInfo,
            @Field("tax_reg_no") String taxRegNo,
            @Field("address") String address,
            @Field("store_address") String storeAddress,
            @Field("note") String note,
            @Field("client_name") String name,
            @Field("client_user_id") String userId
    );

    @FormUrlEncoded
    @POST(ALL_CLIENT_BY_EMAIL)
    Call<List<Client>> clientByEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST(INSERT_PRODUCTS)
    Call<Cash> createProducts(
            @Field("num_loop") int numLoop,
            @Field("product_receipt_id") String[] receiptId,
            @Field("products_price") String[] price,
            @Field("product_quantity") String[] quantity,
            @Field("total") String[] total,
            @Field("notes") String[] notes,
            @Field("item_name") String[] name
    );

}
