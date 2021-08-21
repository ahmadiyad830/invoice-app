package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoCreateProducts {
    private ApiServes serves;
    private static final String TAG = "RepoCreateProducts";

    public RepoCreateProducts() {
        serves = ApiClient.getInstance().getServes();
    }

    public LiveData<Cash> createProducts(
            int numLoop,
            String[] receiptId,
            String[] price,
            String[] quantity,
            String[] total,
            String[] notes,
            String[] name
    ) {
        MutableLiveData<Cash> data = new MutableLiveData<>();
        serves.createProducts(numLoop, receiptId, price, quantity, total, notes, name)
                .enqueue(new Callback<Cash>() {
                    @Override
                    public void onResponse(@NonNull Call<Cash> call, @NonNull Response<Cash> response) {
                        if (response.isSuccessful()) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Cash> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        return data;
    }
}
