package soft.mahmod.yourreceipt.view_model.user_account;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.User.RepoAccount;

public class VMEditAccount extends AndroidViewModel {
    private RepoAccount account;

    public VMEditAccount(@NonNull Application application) {
        super(application);
        account = new RepoAccount(application);
    }

    public LiveData<Cash> changePassword(String pass1, String pass2) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return account.credentialUser(pass1, pass2);
        } else {
            return account.credentialUserTLow(pass1, pass2);
        }
    }

}
