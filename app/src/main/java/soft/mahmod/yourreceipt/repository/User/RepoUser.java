package soft.mahmod.yourreceipt.repository.User;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import soft.mahmod.yourreceipt.model.Cash;

public class RepoUser extends RepoRegistration {
    public RepoUser(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> credentialUser(String email, String password, String newPassword) {
        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");
        getfAuth().getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        changePassword(newPassword);
                    }
                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    e.printStackTrace();
                    getCash().setMessage(e.getMessage());
                    getCash().setError(true);
                    getCash().setCode(TRY_AGAIN);
                    getErrorData().postValue(getCash());
                });
        return getErrorData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void changePassword(String newPassword) {
        getfAuth().getCurrentUser().updatePassword(newPassword)
                .addOnCompleteListener(getApplication().getMainExecutor(), task1 -> {
                    if (task1.isSuccessful()) {
                        getCash().setMessage("success");
                        getCash().setError(false);
                        getCash().setCode(SUCCESS);
                        getErrorData().postValue(getCash());
                    }
                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    e.printStackTrace();
                    getCash().setMessage(e.getMessage());
                    getCash().setError(true);
                    getCash().setCode(TRY_AGAIN);
                    getErrorData().postValue(getCash());
                });
    }


    public LiveData<Cash> credentialUserTLow(String email, String password, String newPassword) {
        AuthCredential credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234");
        getfAuth().getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        changePasswordTLow(newPassword);
                    }
                })
                .addOnFailureListener( e -> {
                    e.printStackTrace();
                    getCash().setMessage(e.getMessage());
                    getCash().setError(true);
                    getCash().setCode(TRY_AGAIN);
                    getErrorData().postValue(getCash());
                });
        return getErrorData();
    }

    private void changePasswordTLow(String newPassword) {
        getfAuth().getCurrentUser().updatePassword(newPassword)
                .addOnCompleteListener( task1 -> {
                    if (task1.isSuccessful()) {
                        getCash().setMessage("success");
                        getCash().setError(false);
                        getCash().setCode(SUCCESS);
                        getErrorData().postValue(getCash());
                    }
                })
                .addOnFailureListener( e -> {
                    e.printStackTrace();
                    getCash().setMessage(e.getMessage());
                    getCash().setError(true);
                    getCash().setCode(TRY_AGAIN);
                    getErrorData().postValue(getCash());
                });
    }
}
