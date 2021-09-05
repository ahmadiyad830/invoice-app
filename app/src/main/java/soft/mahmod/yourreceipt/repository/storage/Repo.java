package soft.mahmod.yourreceipt.repository.storage;

import android.app.Application;
import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import soft.mahmod.yourreceipt.model.Cash;

public class Repo<T> extends soft.mahmod.yourreceipt.repository.database.Repo<T>
        implements OnProgressListener<UploadTask.TaskSnapshot>,
        OnPausedListener<UploadTask.TaskSnapshot> {
    private StorageReference reference;
    private String uid;
    private Application application;
    private MutableLiveData<T> data;
    private Cash cash;
    private FirebaseUser fUser;
    private double progress = 0.0;
    public Repo(Application application) {
        super(application);
        this.application = application;
        data = new MutableLiveData<>();
        cash = new Cash();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseStorage.getInstance().getReference();
    }

    protected double calculator(long bytesTransferred, long totalByteCount) {
        return 100.0 * bytesTransferred / totalByteCount;
    }

    public MutableLiveData<T> getData() {
        return data;
    }

    public StorageReference getRefStorage() {
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

    @Override
    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
        setProgress((100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount());
    }
    @Override
    public void onPaused(@NonNull UploadTask.TaskSnapshot snapshot) {

    }
    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }
}

