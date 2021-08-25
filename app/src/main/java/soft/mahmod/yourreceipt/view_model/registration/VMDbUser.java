package soft.mahmod.yourreceipt.view_model.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.RepoDbUser;

public class VMDbUser extends AndroidViewModel {
    private RepoDbUser repoDbUser;
    public VMDbUser(@NonNull Application application) {
        super(application);
        repoDbUser = new RepoDbUser(application);
    }
    public void newUser(User model,String path){
        repoDbUser.newUser(model,path);
    }
}
