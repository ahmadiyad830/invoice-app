package soft.mahmod.yourreceipt.view_model.auth;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.auth.setting.EditAccount;

public class SettingAuth extends AndroidViewModel {
    private final EditAccount repo;

    public SettingAuth(@NonNull Application application) {
        super(application);
        repo = new EditAccount(application);
    }

    public LiveData<Cash> changePassword(String pass1, String pass2) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repo.credentialUser(pass1, pass2);
        } else {
            return repo.credentialUserTLow(pass1, pass2);
        }
    }

    public LiveData<Cash> forgetPassword(String email) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repo.forgetPassword(email);
        } else {
            return repo.forgetPasswordTLow(email);
        }
    }

    public boolean isConnection() {
        return repo.isConnection();
    }

    public boolean hasCredential() {
        return repo.hasCredential();
    }

    public boolean isVerified() {
        return repo.isVerified();
    }

    public void signOut() {
        repo.signOut();
    }

    @Override
    public void onCleared() {
        super.onCleared();
    }
}
