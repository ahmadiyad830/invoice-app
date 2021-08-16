package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoCreateReceipt {
    private static final String TAG = "RepoCreateReceipt";
    private ApiServes serves;

    public RepoCreateReceipt() {
        serves = ApiClient.getInstance().getServes();
    }


    public LiveData<Cash> createReceipt(Receipt model) {
        MutableLiveData<Cash> data = new MutableLiveData<>();
        serves.createReceipt(model.getUserId(), model.getSubject(), model.getReceiptDate()
                , model.getClientName(), model.getTotalAll(), model.getClientPhone())
                .enqueue(new Callback<Cash>() {
                    @Override
                    public void onResponse(@NonNull Call<Cash> call, @NonNull Response<Cash> response) {
                        Log.d(TAG, "onResponse: "+response.body().getMessage());
                        if (response.isSuccessful()) {
                            data.postValue(response.body());
                            Log.d(TAG, "onResponse: success 1");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Cash> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        return data;
    }
}
