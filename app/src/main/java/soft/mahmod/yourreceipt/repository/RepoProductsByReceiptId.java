package soft.mahmod.yourreceipt.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoProductsByReceiptId {
    private static final String TAG ="RepoProductsByReceiptId" ;
    private ApiServes serves;

    public RepoProductsByReceiptId() {
        serves = ApiClient.getInstance().getServes();
    }

    public LiveData<List<Products>> productsByReceiptId(String receiptId) {
        MutableLiveData<List<Products>> data = new MutableLiveData<>();

        serves.productsByReceiptId(receiptId).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }
}
