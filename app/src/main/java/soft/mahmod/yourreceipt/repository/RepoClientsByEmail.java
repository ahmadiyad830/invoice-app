package soft.mahmod.yourreceipt.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoClientsByEmail {
    private ApiServes serves;

    public RepoClientsByEmail() {
        serves = ApiClient.getInstance().getServes();
    }

    public LiveData<List<Client>> clientsByEmail(String email) {
        MutableLiveData<List<Client>> data = new MutableLiveData<>();

        serves.clientByEmail(email).enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(@NonNull Call<List<Client>> call, @NonNull Response<List<Client>> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Client>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }
}
