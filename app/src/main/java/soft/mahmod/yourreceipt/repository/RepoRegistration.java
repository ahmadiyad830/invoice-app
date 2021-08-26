package soft.mahmod.yourreceipt.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.CallSuper;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import soft.mahmod.yourreceipt.model.Cash;


public class RepoRegistration {
    private Application application;
    private FirebaseAuth fAuth;
    private MutableLiveData<Cash> errorData;
    private MutableLiveData<FirebaseUser> data;
    private boolean hasCredential = false;
    private Cash cash;

    public RepoRegistration(Application application) {
        this.application = application;
        fAuth = FirebaseAuth.getInstance();
        cash = new Cash();
        fAuth.useAppLanguage();
        data = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void signUp(String email, String pass) {
        fAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        verified();
                    }
                }).addOnFailureListener(application.getMainExecutor(), e -> {
            e.printStackTrace();
            cash.setCode(0);
            cash.setMessage(e.getMessage());
            cash.setError(true);
            errorData.setValue(cash);
        });
    }

    public void signUpLowT(String email, String pass) {
        fAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        verified();
                    }
                }).addOnFailureListener(e -> {
            e.printStackTrace();
            cash.setCode(0);
            cash.setMessage(e.getMessage());
            cash.setError(true);
            errorData.setValue(cash);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void signIn(String email, String pass) {
        fAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        if (isVerified()){
                            data.setValue(fAuth.getCurrentUser());
                            cash.setError(false);
                            cash.setMessage("success");
                            cash.setCode(200);
                            errorData.setValue(cash);
                        }else {
                            verified();
                        }
                    }

                }).addOnFailureListener(application.getMainExecutor(), e -> {
            e.printStackTrace();
            cash.setCode(0);
            cash.setMessage(e.getMessage());
            cash.setError(true);
            errorData.setValue(cash);
        });
    }

    public void signInLowT(String email, String pass) {
        fAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (isVerified()){
                            data.setValue(fAuth.getCurrentUser());
                            cash.setError(false);
                            cash.setMessage("success");
                            cash.setCode(200);
                            errorData.setValue(cash);
                        }else {
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

    public void forGetPassword(String email){
        fAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (isVerified()){
                            cash.setError(false);
                            cash.setMessage("Check your email the to recover password in it");
                            cash.setCode(200);
                            errorData.postValue(cash);
                        }else {
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

    public boolean isVerified(){
        return fAuth.getCurrentUser().isEmailVerified();
    }
    public void verified(){
        fAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        cash.setError(true);
                        cash.setMessage("Check your email the to message verified in it");
                        cash.setCode(300);
                        errorData.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    cash.setError(true);
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    errorData.postValue(cash);
                });
    }

    public boolean isHasCredential() {
        return fAuth.getCurrentUser() != null;
    }

    public MutableLiveData<FirebaseUser> getData() {
        return data;
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }
}
