package soft.mahmod.yourreceipt.repository.auth.sign_up;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.auth.MainRegRepo;

public abstract class RepoSignUp<T extends Cash> extends MainRegRepo<T> {


    public RepoSignUp(Application application) {
        super(application);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public abstract LiveData<T> signUp(String email, String password);

    public abstract LiveData<T> signUpTLow(String email, String password);

    @RequiresApi(api = Build.VERSION_CODES.P)
    public abstract void verified();

    public abstract void verifiedTLow();

}
