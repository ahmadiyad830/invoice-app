package soft.mahmod.yourreceipt.repository.auth.setting;

import android.app.Application;

import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.auth.MainRegRepo;

public class RepoEditAccount extends MainRegRepo<User> {
    public RepoEditAccount(Application application) {
        super(application);
    }
}
