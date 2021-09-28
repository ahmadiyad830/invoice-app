package soft.mahmod.yourreceipt.view_model.condition;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.conditions.RepoSignUpConditions;
import soft.mahmod.yourreceipt.model.User;

public class VMSignConditions extends AndroidViewModel {
    RepoSignUpConditions repo;
    public VMSignConditions(@NonNull Application application) {
        super(application);
        repo = new RepoSignUpConditions(application);
    }
    public LiveData<User> signUpCondition(String email,String pass1,String pass2){
        return repo.signUpConditions(email, pass1, pass2);
    }
}
