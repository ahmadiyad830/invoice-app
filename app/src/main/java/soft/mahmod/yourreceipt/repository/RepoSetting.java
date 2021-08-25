package soft.mahmod.yourreceipt.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class RepoSetting {
    private MutableLiveData<Boolean> isSignOut;
    private FirebaseAuth fAuth;
    public RepoSetting() {
        fAuth = FirebaseAuth.getInstance();
        isSignOut = new MutableLiveData<>();
    }
    public void signOut(){
        fAuth.signOut();
        isSignOut.postValue(true);
    }

    public MutableLiveData<Boolean> getIsSignOut() {
        return isSignOut;
    }
}
