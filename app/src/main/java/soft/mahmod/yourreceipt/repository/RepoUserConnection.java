package soft.mahmod.yourreceipt.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoUserConnection {
    private static final String TAG = "RepoUserConnection";
    private ApiServes serves;

    public RepoUserConnection() {
        serves = ApiClient.getInstance().getServes();
    }

    public LiveData<Cash> userConnection(String email) {
        MutableLiveData<Cash> data = new MutableLiveData<>();
        serves.userConnection(email).enqueue(new Callback<Cash>() {
            @Override
            public void onResponse(@NonNull Call<Cash> call, @NonNull Response<Cash> response) {
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
