package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoSignUp {
    private static final String TAG = "RepoSignUp";
    private ApiServes serves;

    public RepoSignUp() {
        serves = ApiClient.getInstance().getServes();
    }

    public LiveData<User> signUp(User user) {
        MutableLiveData<User> data = new MutableLiveData<>();
        serves.signUp(user.getEmail(), user.getPassword(), user.getStoreName(),
                user.getPhoneNum(), user.getStoreAddress())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        try {
                            if (response.errorBody() != null) {
                                Log.d(TAG, "onResponse: " + response.code()+response.errorBody().string());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (response.isSuccessful()) {
                            data.setValue(response.body());
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
