package soft.mahmod.yourreceipt.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RepoRegistration {
    private Application application;
    private FirebaseAuth fAuth;


    private MutableLiveData<String> errorData;

    private MutableLiveData<FirebaseUser> data;
    private boolean hasCredential = false;

    public RepoRegistration(Application application) {
        this.application = application;
        fAuth = FirebaseAuth.getInstance();
        fAuth.useAppLanguage();
        data = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void signUp(String email, String pass) {
        fAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        data.setValue(fAuth.getCurrentUser());
                        errorData.setValue("true");
                    }
                }).addOnFailureListener(application.getMainExecutor(), e -> {
            e.printStackTrace();
            errorData.setValue(e.getMessage());
        });
    }

    public void signUpLowT(String email, String pass) {
        fAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        data.setValue(fAuth.getCurrentUser());
                        errorData.setValue("true");
                    }
                }).addOnFailureListener(e -> {
            e.printStackTrace();
            errorData.setValue(e.getMessage());
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void signIn(String email, String pass) {
        fAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        data.setValue(fAuth.getCurrentUser());
                        errorData.setValue("true");
                    }

                }).addOnFailureListener(application.getMainExecutor(), e -> {
            e.printStackTrace();
            errorData.setValue(e.getMessage());
        });
    }

    public void signInLowT(String email, String pass) {
        fAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        data.setValue(fAuth.getCurrentUser());
                        errorData.setValue("true");
                    }

                }).addOnFailureListener(e -> {
            e.printStackTrace();
            errorData.setValue(e.getMessage());
        });
    }

    public boolean isHasCredential() {
        return fAuth.getCurrentUser() != null;
    }

    public MutableLiveData<FirebaseUser> getData() {
        return data;
    }

    public MutableLiveData<String> getErrorData() {
        return errorData;
    }
}
