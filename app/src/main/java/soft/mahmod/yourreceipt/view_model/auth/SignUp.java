package soft.mahmod.yourreceipt.view_model.auth;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.auth.sign_up.SignUpRepo;

public class SignUp extends AndroidViewModel {
    private SignUpRepo signUpRepo;

    public SignUp(@NonNull Application application) {
        super(application);
        signUpRepo = new SignUpRepo(application);
    }

    public LiveData<User> signIn(String email, String password) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return signUpRepo.signUp(email, password);
        } else {
            return signUpRepo.signUpTLow(email, password);
        }
    }
}
