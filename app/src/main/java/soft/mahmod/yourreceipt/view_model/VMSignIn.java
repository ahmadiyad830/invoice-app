package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.RepoSignIn;

public class VMSignIn extends ViewModel {
    private RepoSignIn repoSignIn;

    public VMSignIn() {
        repoSignIn = new RepoSignIn();
    }
    public LiveData<User> signIn(String email,String password){
        return repoSignIn.signIn(email, password);
    }
}
