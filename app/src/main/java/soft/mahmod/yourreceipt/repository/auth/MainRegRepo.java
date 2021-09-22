package soft.mahmod.yourreceipt.repository.auth;

import android.app.Application;

import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.statics.StateCode;
import soft.mahmod.yourreceipt.statics.StateMessage;

public class MainRegRepo<T extends Cash> implements DatabaseUrl, StateCode, StateMessage {
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private String uid;
    protected MutableLiveData<T> data;
    private Application application;

    public MainRegRepo(Application application) {
        this.application = application;
        data = new MutableLiveData<>();
        this.fAuth = FirebaseAuth.getInstance();
        this.fUser = fAuth.getCurrentUser();
        if (fUser != null)
            this.uid = fUser.getUid();
    }

    public FirebaseAuth getfAuth() {
        return fAuth;
    }

    public FirebaseUser getfUser() {
        try {
            return fUser;
        }catch (NullPointerException e){
            return FirebaseAuth.getInstance().getCurrentUser();
        }
    }

    public String getUid() {
        return uid;
    }

    public MutableLiveData<T> getData() {
        return data;
    }

    public Application getApplication() {
        return application;
    }

    public boolean isVerified() {
        return fAuth.getCurrentUser().isEmailVerified();
    }

    public boolean hasCredential() {
        return fAuth.getCurrentUser() != null;
    }

    public String getResources (@StringRes int res){
        return application.getResources().getString(res);
    }
}
