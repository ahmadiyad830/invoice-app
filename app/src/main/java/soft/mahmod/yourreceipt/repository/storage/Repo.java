package soft.mahmod.yourreceipt.repository.storage;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import soft.mahmod.yourreceipt.model.Cash;

public class Repo<T> {
    private StorageReference reference;
    private String uid;
    private Application application;
    private MutableLiveData<T> data;
    private Cash cash ;
    public Repo(Application application) {
        this.application = application;
        data = new MutableLiveData<>();
        cash = new Cash();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseStorage.getInstance().getReference();
    }

    public MutableLiveData<T> getData() {
        return data;
    }

    public StorageReference getReference() {
        return reference;
    }

    public String getUid() {
        return uid;
    }

    public Application getApplication() {
        return application;
    }

    public Cash getCash() {
        return cash;
    }
}

