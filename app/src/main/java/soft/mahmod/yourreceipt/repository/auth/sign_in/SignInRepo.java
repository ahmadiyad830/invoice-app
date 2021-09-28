package soft.mahmod.yourreceipt.repository.auth.sign_in;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.database.RepoUser;
import soft.mahmod.yourreceipt.statics.StateCode;
import soft.mahmod.yourreceipt.statics.StateMessage;

public class SignInRepo extends RepoSignIn<User> {
    private final User user = new User();

    public SignInRepo(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public LiveData<User> signIn(String email, String password) {
        getfAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (!task.isSuccessful()) {
                        user.setError(true);
                        user.setMessage(getResources(R.string.try_again));
                        user.setCode(StateCode.TRY_AGAIN);
                    } else {
                        if (isVerified()) {
                            RepoUser repoUser = new RepoUser(getApplication());
                            this.user.setError(false);
                            this.user.setMessage(getResources(R.string.success));
                            this.user.setCode(StateCode.SUCCESS);
                        } else {
                            user.setError(true);
                            user.setMessage(getResources(R.string.active_your_account));
                            user.setCode(StateCode.ACTIVE);
                        }
                    }
                    data.setValue(user);
                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    user.setError(true);
                    user.setMessage(e.getLocalizedMessage());
                    user.setCode(StateCode.TRY_AGAIN);
                    data.setValue(user);
                });
        return data;
    }

    @Override
    public LiveData<User> signInTLow(String email, String password) {
        getfAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        user.setError(true);
                        user.setMessage(getResources(R.string.try_again));
                        user.setCode(StateCode.TRY_AGAIN);
                    } else {
                        if (isVerified()) {
                            user.setError(false);
                            user.setMessage(getResources(R.string.success));
//                            user.setMessage(StateMessage.SUCCESS);
                            user.setCode(StateCode.SUCCESS);
                        } else {
                            user.setError(true);
                            user.setMessage(getResources(R.string.active_your_account));
                            user.setCode(StateCode.ACTIVE);
                        }
                    }
                    data.setValue(user);

                })
                .addOnFailureListener(e -> {
                    user.setError(true);
                    user.setMessage(e.getLocalizedMessage());
                    user.setCode(StateCode.TRY_AGAIN);
                    data.setValue(user);
                });
        return data;
    }

}
