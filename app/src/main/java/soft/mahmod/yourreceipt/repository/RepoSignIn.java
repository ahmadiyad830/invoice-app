package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.internal.http2.ErrorCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoSignIn {
    private static final String TAG = "RepoSignIn";
    private ApiServes serves;

    public RepoSignIn() {
        this.serves = ApiClient.getInstance().getServes();
    }

    public LiveData<User> signIn(String email, String password) {
        MutableLiveData<User> data = new MutableLiveData<>();
        serves.signIn(email, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response.errorBody().string());
                        Log.d(TAG, "onResponse: "+json.toString());
                        User user = new User(json.getString("message")
                                ,json.getBoolean("error"),json.getInt("code"));
                        data.setValue(user);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
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
