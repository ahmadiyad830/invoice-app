package soft.mahmod.yourreceipt.view_model.auth;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.auth.sign_in.SignInRepo;

public class VMSignIn extends AndroidViewModel {
    private final SignInRepo signInRepo;

    public VMSignIn(@NonNull Application application) {
        super(application);
        signInRepo = new SignInRepo(application);
    }

    public LiveData<User> signIn(String email, String password) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return signInRepo.signIn(email, password);
        } else {
            return signInRepo.signInTLow(email, password);
        }
    }

    public boolean isConnection() {
        return signInRepo.isConnection();
    }
}
