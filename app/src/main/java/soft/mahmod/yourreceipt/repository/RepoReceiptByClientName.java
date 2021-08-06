package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoReceiptByClientName {
    private static final String TAG = "RepoReceiptByClientName";
    private ApiServes serves;

    public RepoReceiptByClientName() {
        serves = ApiClient.getInstance().getServes();
    }

    public LiveData<List<Receipt>> receiptByClientName(String clientName) {
        MutableLiveData<List<Receipt>> data = new MutableLiveData<>();
        serves.getReceiptByClient(clientName).enqueue(new Callback<List<Receipt>>() {
            @Override
            public void onResponse(@NonNull Call<List<Receipt>> call, @NonNull Response<List<Receipt>> response) {
                Log.d(TAG, "onResponse: "+response.body());
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Receipt>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }
}
