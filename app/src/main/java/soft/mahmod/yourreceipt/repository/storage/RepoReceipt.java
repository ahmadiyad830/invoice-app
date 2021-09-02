package soft.mahmod.yourreceipt.repository.storage;

import android.app.Application;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import soft.mahmod.yourreceipt.model.Cash;

public class RepoReceipt extends Repo<Cash>{
    private StorageReference storageReference;
    private DatabaseReference reference;
    private Uri uri;
    public RepoReceipt(Application application) {
        super(application);
        storageReference = FirebaseStorage.getInstance().getReference("image");
        reference = FirebaseDatabase.getInstance().getReference();
    }
    public void uploadFile(){
        StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                taskSnapshot.getMetadata().getReference().getDownloadUrl()
            }
        });
    }
}
