package soft.mahmod.yourreceipt.repository.auth.setting;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.statics.StateCode;


public class RepoSettingAuth extends RepoEditAccount {
    private final Cash cash = new Cash();

    public RepoSettingAuth(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public LiveData<Cash> credentialUser(String password, String newPassword) {
        AuthCredential credential = EmailAuthProvider.getCredential(getfAuth().getCurrentUser().getEmail(), password);
        getfAuth().getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        changePassword(password, newPassword);
                    }
                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    e.printStackTrace();
                    cash.setMessage(e.getLocalizedMessage());
                    cash.setError(true);
                    cash.setCode(StateCode.TRY_AGAIN);
                    data.postValue(cash);
                });
        return data;
    }

    @Override
    public LiveData<Cash> credentialUserTLow(String password, String newPassword) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(getfAuth().getCurrentUser().getEmail(), password);
        getfAuth().getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        changePasswordTLow(password, newPassword);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setMessage(e.getLocalizedMessage());
                    cash.setError(true);
                    cash.setCode(StateCode.TRY_AGAIN);
                    data.postValue(cash);
                });
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void changePassword(String pass1, String pass2) {
        getfAuth().getCurrentUser().updatePassword(pass2)
                .addOnCompleteListener(getApplication().getMainExecutor(), task1 -> {
                    if (task1.isSuccessful()) {

                        cash.setMessage(getResources(R.string.success));
                        cash.setError(false);
                        cash.setCode(StateCode.SUCCESS);
                        data.postValue(cash);
                        updatePassword(pass2);
                    }
                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    e.printStackTrace();
                    cash.setMessage(e.getLocalizedMessage());
                    cash.setError(true);
                    cash.setCode(StateCode.TRY_AGAIN);
                    data.postValue(cash);
                });
    }

    private void updatePassword(String pass2) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(USER).child(getUid()).child(PASSWORD)
                .setValue(pass2);

    }

    @Override
    public void changePasswordTLow(String pass1, String pass2) {
        getfAuth().getCurrentUser().updatePassword(pass2)
                .addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        cash.setMessage(getResources(R.string.success));
                        cash.setError(false);
                        cash.setCode(StateCode.SUCCESS);
                        data.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setMessage(e.getLocalizedMessage());
                    cash.setError(true);
                    cash.setCode(StateCode.TRY_AGAIN);
                    data.postValue(cash);
                });
    }

    @Override
    public void signOut() {
        cash.setError(false);
        cash.setCode(StateCode.SUCCESS);
        cash.setMessage(getResources(R.string.success));
        data.setValue(cash);
        getfAuth().signOut();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public LiveData<Cash> forgetPassword(String email) {
        getfAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        if (isVerified()) {
                            cash.setError(false);
                            cash.setMessage(getResources(R.string.check_email));
                            cash.setCode(200);
                            data.postValue(cash);
                        } else {
                            cash.setError(false);
                            cash.setMessage(getResources(R.string.active_your_account));
                            cash.setCode(StateCode.ACTIVE);
                            data.postValue(cash);
                        }
                    }
                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    cash.setError(true);
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    data.postValue(cash);
                });
        return data;
    }

    @Override
    public LiveData<Cash> forgetPasswordTLow(String email) {
        getfAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (isVerified()) {
                            cash.setError(false);
                            cash.setMessage(getResources(R.string.check_email));
                            cash.setCode(200);
                            data.postValue(cash);
                        } else {
                            cash.setError(false);
                            cash.setMessage(getResources(R.string.active_your_account));
                            cash.setCode(StateCode.ACTIVE);
                            data.postValue(cash);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    cash.setError(true);
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    data.postValue(cash);
                });
        return data;
    }


}
