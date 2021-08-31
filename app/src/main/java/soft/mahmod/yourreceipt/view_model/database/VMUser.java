package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.database.RepoUser;

public class VMUser extends AndroidViewModel {
    private RepoUser repoDbUser;

    public VMUser(@NonNull Application application) {
        super(application);
        repoDbUser = new RepoUser(application);
    }


    public LiveData<Cash> postUser(User model) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repoDbUser.postUser(model);
        } else return repoDbUser.postUserTLow(model);
    }
    public LiveData<User> getUser(){
        return repoDbUser.getUser();
    }


}
