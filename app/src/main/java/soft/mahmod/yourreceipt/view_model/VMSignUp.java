package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.RepoSignUp;

public class VMSignUp extends ViewModel {
    private RepoSignUp repoSignUp;

    public VMSignUp() {
        repoSignUp = new RepoSignUp();
    }
    public LiveData<User> signUp(User user){
        return repoSignUp.signUp(user);
    }
}
