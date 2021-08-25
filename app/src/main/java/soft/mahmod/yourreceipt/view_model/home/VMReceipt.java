package soft.mahmod.yourreceipt.view_model.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.repository.home.RepoHome;

public class VMReceipt extends ViewModel {
    private static final String TAG = "VMReceipt";
    private DatabaseReference reference;
    private MutableLiveData<List<Receipt>> data;
    private MutableLiveData<Cash> errorData;
    private RepoHome repoHome;
    public VMReceipt() {
        repoHome = new RepoHome();
        data = repoHome.getData();
        errorData = repoHome.getErrorData();
    }
    public void getAllReceipt(){
        Log.d(TAG, "getAllReceipt: ");
        repoHome.getAllReceipt();
    }

    public MutableLiveData<List<Receipt>> getData() {
        return data;
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }
}
