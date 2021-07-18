package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoSignIn {
    private ApiServes serves;

    public RepoSignIn() {
        this.serves = ApiClient.getInstance().getServes();
    }

    public LiveData<User> signIn(String email, String password) {
        MutableLiveData<User> data = new MutableLiveData<>();
        serves.signIn(email, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    if (!user.getError()){
                        data.setValue(user);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }
}
