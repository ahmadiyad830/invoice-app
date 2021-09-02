package soft.mahmod.yourreceipt.repository.storage;

import android.app.Application;
import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import soft.mahmod.yourreceipt.model.Cash;

public class Repo<T> {
    private StorageReference reference;
    private String uid;
    private Application application;
    private MutableLiveData<T> data;
    private Cash cash ;
    private FirebaseUser fUser;
    public Repo(Application application) {
        this.application = application;
        data = new MutableLiveData<>();
        cash = new Cash();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseStorage.getInstance().getReference("image/");
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
    public String getFileExtension(Uri uri){
        ContentResolver cR = application.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));
    }
}

