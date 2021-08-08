package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoCreateItem {
    private static final String TAG = "RepoCreateItem";
    private ApiServes serves;

    public RepoCreateItem() {
        serves = ApiClient.getInstance().getServes();
    }

    public LiveData<Cash> createItem(Items model) {
        MutableLiveData<Cash> data = new MutableLiveData<>();
        serves.createItem(model.getUserId(), model.getItemName(), model.getItemPrice()
                , model.getDiscount(), model.getItemTax(), model.getItemNote())
                .enqueue(new Callback<Cash>() {
                    @Override
                    public void onResponse(@NonNull Call<Cash> call, @NonNull Response<Cash> response) {
                        Log.d(TAG, "onResponse: "+response.body().toString());
                        if (response.isSuccessful()){
                            data.setValue(response.body());
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
