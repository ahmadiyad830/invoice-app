package soft.mahmod.yourreceipt.view_model.registration;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.RepoRegistration;

public class VMAuthReg extends AndroidViewModel {
    private static final String TAG = "VMAuthReg";
    private RepoRegistration repoRegistration;
    private MutableLiveData<FirebaseUser> data;
    private MutableLiveData<Cash> errorData;
    public VMAuthReg(@NonNull Application application) {
        super(application);
        repoRegistration = new RepoRegistration(application);
        data = repoRegistration.getData();
        errorData = repoRegistration.getErrorData();
    }
    public void signUp(String email,String password){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            repoRegistration.signUp(email, password);
        }else {
            repoRegistration.signUpLowT(email, password);
        }
    }
    public void signIn(String email, String password){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            repoRegistration.signIn(email, password);
            Log.d(TAG, "signIn: ");
        }else {
            repoRegistration.signInLowT(email, password);
            Log.d(TAG, "signInLowT: ");
        }
    }
    public boolean hasCredential(){
        return repoRegistration.isHasCredential();
    }
    public void forgetPassword(String email){
        repoRegistration.forGetPassword(email);
    }

    public MutableLiveData<FirebaseUser> getData() {
        return data;
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }
}
