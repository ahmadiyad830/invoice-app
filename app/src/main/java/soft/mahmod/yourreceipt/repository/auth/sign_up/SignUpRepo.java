package soft.mahmod.yourreceipt.repository.auth.sign_up;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.statics.StateCode;
import soft.mahmod.yourreceipt.statics.StateMessage;

public class SignUpRepo extends RepoSignUp<User> {
    private final User user = new User();

    public SignUpRepo(Application application) {
        super(application);

    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public LiveData<User> signUp(String email, String password) {
        getfAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( getApplication().getMainExecutor(),task -> {
                    if (!task.isSuccessful()) {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthUserCollisionException | FirebaseAuthInvalidCredentialsException e) {
                            user.setError(true);
//                            user.setCode(Integer.parseInt(e.getErrorCode()));
                            user.setCode(StateCode.TRY_AGAIN);
                            user.setMessage(getResources(R.string.try_again));
                            data.setValue(user);
                        } catch (Exception e) {
                            user.setError(true);
                            user.setCode(StateCode.UNKNOWN);
                            user.setMessage(e.getLocalizedMessage());
                            data.setValue(user);
                        }
                    } else {
                        verified();
                    }
                })
                .addOnFailureListener(getApplication().getMainExecutor(),e -> {
                    user.setError(true);
                    user.setCode(StateCode.UNKNOWN);
                    user.setMessage(e.getLocalizedMessage());
                    data.setValue(user);
                });
        return data;
    }

    @Override
    public LiveData<User> signUpTLow(String email, String password) {
        getfAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthUserCollisionException | FirebaseAuthInvalidCredentialsException e) {
                            user.setError(true);
//                            user.setCode(Integer.parseInt(e.getErrorCode()));
                            user.setCode(StateCode.TRY_AGAIN);
                            user.setMessage(e.getLocalizedMessage());
                            data.setValue(user);
                        } catch (Exception e) {
                            user.setError(true);
                            user.setCode(StateCode.UNKNOWN);
                            user.setMessage(e.getLocalizedMessage());
                            data.setValue(user);
                        }
                    } else {
                        verifiedTLow();
                    }
                })
                .addOnFailureListener(e -> {
                    user.setError(true);
                    user.setCode(StateCode.UNKNOWN);
                    user.setMessage(e.getLocalizedMessage());
                    data.setValue(user);
                });
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void verified() {
        getfAuth().getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(getApplication().getMainExecutor(),task -> {
                    if (task.isSuccessful()) {
                        user.setError(false);
                        user.setCode(StateCode.SUCCESS);
                        user.setMessage(getResources(R.string.success));
                        data.setValue(user);
                    }
                })
                .addOnFailureListener(getApplication().getMainExecutor(),e -> {
                    user.setError(true);
                    user.setCode(StateCode.UNKNOWN);
                    user.setMessage(e.getLocalizedMessage());
                    data.setValue(user);
                });
    }

    @Override
    public void verifiedTLow() {
        getfAuth().getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.setError(false);
                        user.setCode(StateCode.SUCCESS);
                        user.setMessage(getResources(R.string.success));
                        data.setValue(user);
                    }
                })
                .addOnFailureListener(e -> {
                    user.setError(true);
                    user.setCode(StateCode.UNKNOWN);
                    user.setMessage(e.getLocalizedMessage());
                    data.setValue(user);
                });
    }

}
