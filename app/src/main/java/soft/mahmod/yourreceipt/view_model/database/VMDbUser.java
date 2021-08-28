package soft.mahmod.yourreceipt.view_model.database;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.Database.RepoUser;

public class VMDbUser extends ViewModel {
    private RepoUser repoDbUser;
    private MutableLiveData<User> data;
    private MutableLiveData<Cash> errorData;

    public VMDbUser() {
        repoDbUser = new RepoUser();
        data = repoDbUser.getData();
        errorData = repoDbUser.getErrorData();
    }
    public void postUser(User model){
        repoDbUser.postUser(model);
    }

    public void isUserActive(){
        repoDbUser.readData();
    }
    public void readUser(){
        repoDbUser.readData();
    }

    public MutableLiveData<User> getData() {
        return data;
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }
}
