package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoItemsByEmail {
    private static final String TAG = "RepoItemsByEmail";
    private ApiServes serves;

    public RepoItemsByEmail() {
        serves = ApiClient.getInstance().getServes();
    }
    public LiveData<List<Items>> itemByEmail(String email){
        MutableLiveData<List<Items>> data = new MutableLiveData<>();
        serves.itemsByEmail(email).enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(@NonNull Call<List<Items>> call, @NonNull Response<List<Items>> response) {
                Log.d(TAG, "onResponse: "+response.body().get(1).getItemName());
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Items>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }
}
