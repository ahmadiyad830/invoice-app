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
    public LiveData<Cash> changePassword(String email,String oldPass,String password ,String passwordC){
        return changePassword.changePassword(email,oldPass, password,passwordC);
    }
}
