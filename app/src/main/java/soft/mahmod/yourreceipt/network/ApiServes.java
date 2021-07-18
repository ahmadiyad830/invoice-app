package soft.mahmod.yourreceipt.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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
    Call<Void> signUp(@Field("email") String email,
                      @Field("password") String password,
                      @Field("store_name") String username,
                      @Field("phone_num") String userType,
                      @Field("store_address") String pharmacyName);
    @FormUrlEncoded
    @POST(ALL_RECEIPT_BY_EMAIL)
    Call<List<Receipt>> getReceiptByEmail(@Field("email") String email);
    @FormUrlEncoded
    @POST(ALL_RECEIPT_BY_CLIENT_NAME)
    Call<List<Receipt>> getReceiptByClient(@Field("client_name") String clientName);
    @FormUrlEncoded
    @POST(CREATE_RECEIPT)
    Call<Void> createReceipt(@Field("'user_id'") String userId,
                             @Field("'subject") String subject,
                             @Field("'receipt_date'") String receiptDate,
                             @Field("'client_name'") String clientName);

}
