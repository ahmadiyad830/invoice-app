package soft.mahmod.yourreceipt.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import soft.mahmod.yourreceipt.model.Cash;
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
    Call<User> signUp(@Field("email") String email,
                      @Field("password") String password,
                      @Field("store_name") String storeName,
                      @Field("phone_num") String phoneNumber,
                      @Field("store_address") String storeAddress);

    @FormUrlEncoded
    @POST(ALL_RECEIPT_BY_EMAIL)
    Call<List<Receipt>> getReceiptByEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST(ALL_RECEIPT_BY_CLIENT_NAME)
    Call<List<Receipt>> getReceiptByClient(@Field("client_name") String clientName);

    //    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST(CREATE_RECEIPT)
    Call<Cash> createReceipt(@Field("user_id") String userId,
                             @Field("subject") String subject,
                             @Field("receipt_date") String receiptDate,
                             @Field("client_name") String clientName,
                             @Field("total_all") String totalAll,
                             @Field("client_phone") String clientPhone);

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
    Call<Cash> changePassword(@Field("email") String email, @Field("password") String password);

}
