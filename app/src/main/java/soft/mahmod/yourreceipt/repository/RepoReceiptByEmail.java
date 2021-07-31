package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoReceiptByEmail {

    private static final String TAG = "RepoReceiptByEmail";

    private ApiServes serves;

    public RepoReceiptByEmail() {
        serves = ApiClient.getInstance().getServes();
    }
    public LiveData<List<Receipt>> getReceiptByEmail(String email){
        MutableLiveData<List<Receipt>> data = new MutableLiveData<>();
        serves.getReceiptByEmail(email).enqueue(new Callback<List<Receipt>>() {
            @Override
            public void onResponse(@NonNull Call<List<Receipt>> call,@NonNull  Response<List<Receipt>> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }
                Log.d(TAG, "onResponse: "+response.isSuccessful());
            }

            @Override
            public void onFailure(@NonNull Call<List<Receipt>> call,@NonNull  Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }
}
