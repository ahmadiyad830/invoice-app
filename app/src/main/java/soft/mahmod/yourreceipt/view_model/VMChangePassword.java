package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.RepoChangePassword;

public class VMChangePassword extends ViewModel {
    private RepoChangePassword changePassword;

    public VMChangePassword() {
        changePassword = new RepoChangePassword();
    }
    public LiveData<Cash> changePassword(String email,String password){
        return changePassword.changePassword(email, password);
    }
}
