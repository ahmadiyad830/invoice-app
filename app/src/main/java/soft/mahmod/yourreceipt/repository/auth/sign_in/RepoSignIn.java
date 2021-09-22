package soft.mahmod.yourreceipt.repository.auth.sign_in;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.auth.MainRegRepo;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.statics.StateCode;
import soft.mahmod.yourreceipt.statics.StateMessage;

public abstract class RepoSignIn<T extends Cash> extends MainRegRepo<T>{

    public RepoSignIn(Application application) {
        super(application);
    }

    protected abstract LiveData<T> signIn(String email, String password);

    protected abstract LiveData<T> signInTLow(String email, String password);

}
