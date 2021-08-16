package soft.mahmod.yourreceipt.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.internal.JavaVersion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoCreateClient {
    private ApiServes serves;

    public RepoCreateClient() {
        serves = ApiClient.getInstance().getServes();
    }

    public LiveData<Cash> createClient(Client model) {
        MutableLiveData<Cash> data = new MutableLiveData<>();
        serves.createClient(
                model.getEmail(), model.getPhone(), model.getAddInfo()
                , model.getTaxRegNo(), model.getAddress(), model.getStoreAddress()
                , model.getNote(), model.getName(), model.getUserId()
        ).enqueue(new Callback<Cash>() {
            @Override
            public void onResponse(@NonNull Call<Cash> call, @NonNull Response<Cash> response) {
                if (response.isSuccessful()) {
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
