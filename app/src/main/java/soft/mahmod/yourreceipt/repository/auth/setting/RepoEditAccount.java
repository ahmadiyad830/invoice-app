package soft.mahmod.yourreceipt.repository.auth.setting;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.auth.MainRegRepo;

public abstract class RepoEditAccount extends MainRegRepo<Cash> {
    public RepoEditAccount(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public abstract LiveData<Cash> credentialUser(String password, String newPassword);

    public abstract LiveData<Cash> credentialUserTLow(String password, String newPassword);

    @RequiresApi(api = Build.VERSION_CODES.P)
    public abstract void changePassword(String pass1, String pass2);

    public abstract void changePasswordTLow(String pass1, String pass2);

    public abstract void signOut();

    public abstract LiveData<Cash> forgetPassword(String email);

    public abstract LiveData<Cash> forgetPasswordTLow(String email);
}
