package soft.mahmod.yourreceipt.repository.User;

import android.app.Application;
import android.os.Build;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.ActionProvider;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;
import soft.mahmod.yourreceipt.statics.ErrorCode;


public class RepoRegistration implements DatabaseUrl {
    private Application application;
    private FirebaseAuth fAuth;
    private MutableLiveData<Cash> errorData;
    private MutableLiveData<User> data;
    private boolean hasCredential = false;
    private Cash cash;
    private String tryAgain = "Or restart the app";
    private DatabaseReference reference;

    public RepoRegistration(Application application) {
        this.application = application;
        fAuth = FirebaseAuth.getInstance();
        fAuth.useAppLanguage();
        reference = FirebaseDatabase.getInstance().getReference();
        cash = new Cash();
        data = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void signIn(String email, String pass) {
        fAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        if (isVerified()) {
                            cash.setError(false);
                            cash.setMessage("success");
                            cash.setCode(200);
                            errorData.setValue(cash);
                        } else {
                            verified();
                        }
                    }
                })
                .addOnFailureListener(application.getMainExecutor(), e -> {
                    e.printStackTrace();
                    cash.setCode(UNKNOWN);
                    cash.setMessage(e.getMessage());
                    cash.setError(true);
                    errorData.setValue(cash);
                });
    }

    public void signInLowT(String email, String pass) {
        fAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (isVerified()) {
                            cash.setError(false);
                            cash.setMessage("success");
                            cash.setCode(200);
                            errorData.setValue(cash);
                        } else {
                            verified();
                        }
                    }

                }).addOnFailureListener(e -> {
            e.printStackTrace();
            cash.setCode(0);
            cash.setMessage(e.getMessage());
            cash.setError(true);
            errorData.setValue(cash);
        });
    }

    public void forGetPassword(String email) {
        fAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (isVerified()) {
                            cash.setError(false);
                            cash.setMessage("Check your email the to recover password in it\n" + tryAgain);
                            cash.setCode(200);
                            errorData.postValue(cash);
                        } else {
                            verified();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    cash.setError(true);
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    errorData.postValue(cash);
                });
    }


    public boolean isVerified() {
        return fAuth.getCurrentUser().isEmailVerified();
    }

    public void verified() {
        if (fAuth.getCurrentUser() != null) {
            fAuth.getCurrentUser().sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            cash.setError(false);
                            cash.setMessage("Check your email the to message verified in it\n" + tryAgain);
                            cash.setCode(SUCCESS);
                            errorData.postValue(cash);
                        }
                    })
                    .addOnFailureListener(e -> {
                        cash.setError(true);
                        cash.setMessage(e.getMessage());
                        cash.setCode(0);
                        errorData.postValue(cash);
                    });
        } else {
            cash.setError(true);
            cash.setMessage("try again");
            cash.setCode(TRY_AGAIN);
            errorData.postValue(cash);
        }

    }

    public boolean isHasCredential() {
        return fAuth.getCurrentUser() != null;
    }


    public MutableLiveData<User> getData() {
        return data;
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void signUp(String email, String pass) {
        fAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        verified();
                    }
                })
                .addOnFailureListener(application.getMainExecutor(), e -> {
                    cash.setError(true);
                    cash.setMessage(e.getMessage());
                    cash.setCode(UNKNOWN);
                });
    }

    public void signUpLowT(String email, String pass) {
        fAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        verified();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setCode(0);
                    cash.setMessage(e.getMessage());
                    cash.setError(true);
                    errorData.setValue(cash);
                });
    }

    public FirebaseAuth getfAuth() {
        return fAuth;
    }

    public Application getApplication() {
        return application;
    }

    public Cash getCash() {
        return cash;
    }

    public void signOut() {
        cash.setError(false);
        cash.setCode(SUCCESS);
        cash.setMessage("success");
        fAuth.signOut();
    }
}
