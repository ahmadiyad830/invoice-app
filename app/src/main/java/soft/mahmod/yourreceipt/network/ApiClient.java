package soft.mahmod.yourreceipt.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import soft.mahmod.yourreceipt.statics.ApiURLS;

public class ApiClient implements ApiURLS{
    private static ApiClient instance;
    private Retrofit retrofit;

    private ApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(STATIC_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public synchronized static ApiClient getInstance(){
        if (instance==null){
            instance = new ApiClient();
        }
        return instance;
    }


    public ApiServes getServes(){
        return retrofit.create(ApiServes.class);
    }
}
