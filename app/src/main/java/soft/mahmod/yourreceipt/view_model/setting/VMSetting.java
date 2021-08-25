package soft.mahmod.yourreceipt.view_model.setting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.repository.RepoSetting;

public class VMSetting extends ViewModel {
    private RepoSetting repoSetting;
    private MutableLiveData<Boolean> isSignOut;

    public VMSetting() {
        repoSetting = new RepoSetting();
        isSignOut = repoSetting.getIsSignOut();
    }
    public void signOut(){
        repoSetting.signOut();
    }
    public MutableLiveData<Boolean> getIsSignOut() {
        return isSignOut;
    }
}
