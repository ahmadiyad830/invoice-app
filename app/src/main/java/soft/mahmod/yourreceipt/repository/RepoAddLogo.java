package soft.mahmod.yourreceipt.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.network.ApiClient;
import soft.mahmod.yourreceipt.network.ApiServes;

public class RepoAddLogo {
    private static final String TAG = "RepoAddLogo";
    private ApiServes serves;

    public RepoAddLogo() {
        serves  = ApiClient.getInstance().getServes();
    }
    public LiveData<Cash> addLogo(String path,String email){
        MutableLiveData<Cash> data = new MutableLiveData<>();
        serves.addLogo(path, email).enqueue(new Callback<Cash>() {
            @Override
            public void onResponse(@NonNull Call<Cash> call, @NonNull Response<Cash> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response.errorBody().string());
                        Log.d(TAG, "onResponse: "+json.toString());
                        Cash cash = new Cash(json.getString("message")
                                ,json.getBoolean("error"),json.getInt("code"));
                        data.setValue(cash);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cash> call, @NonNull Throwable t) {

            }
        });
        return data;
    }
}
