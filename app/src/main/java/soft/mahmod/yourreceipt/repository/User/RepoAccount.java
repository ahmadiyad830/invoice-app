package soft.mahmod.yourreceipt.repository.User;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import soft.mahmod.yourreceipt.model.Cash;

public class RepoAccount extends RepoRegistration implements OnEditAccount{
    public RepoAccount(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> credentialUser(String password, String newPassword) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(getfAuth().getCurrentUser().getEmail(), password);
        getfAuth().getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        changePassword(password, newPassword);
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

    public LiveData<Cash> credentialUserTLow(String password, String newPassword) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(getfAuth().getCurrentUser().getEmail(), password);
        getfAuth().getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        changePasswordTLow(password, newPassword);
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

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void changePassword( String pass1, String pass2) {
        getfAuth().getCurrentUser().updatePassword(pass2)
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

    @Override
    public void changePasswordTLow(String pass1, String pass2) {
        getfAuth().getCurrentUser().updatePassword(pass2)
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

    @Override
    public void uploadImage(String path) {

    }

    @Override
    public void changeEmail() {

    }

    @Override
    public void forgetPassword(String email) {

    }
}
