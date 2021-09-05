package soft.mahmod.yourreceipt.repository.storage;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.StorageReference;

import soft.mahmod.yourreceipt.model.Cash;

public class RepoLogo extends Repo<Cash> {
    public static final String TAG = "RepoLogo";
    public RepoLogo(Application application) {
        super(application);

    }

    private StorageReference pathFile(Uri uri) {
        return getRefStorage().child(getUid())
                .child("logo"+"." + getFileExtension(uri));
    }
    public void addUserImage(Uri uri){
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setPhotoUri(uri);
        getfUser().updateProfile(builder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postLogo(Uri uri) {
        pathFile(uri).putFile(uri)
                .addOnSuccessListener(getApplication().getMainExecutor(), task -> {
                    task.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(getApplication().getMainExecutor(), uri1 -> {
                        addUserImage(uri1);
                        getCash().setError(false);
                        getCash().setMessage(uri1.toString());
                        getData().setValue(getCash());
                    }).addOnFailureListener(getApplication().getMainExecutor(),e -> {
                        getCash().setError(true);
                        getCash().setMessage(e.getMessage());
                        getData().setValue(getCash());
                    });
                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    getCash().setError(true);
                    getCash().setMessage(e.getMessage());
                    getData().setValue(getCash());
                })
                .addOnProgressListener(getApplication().getMainExecutor(), snapshot -> {
                    //TODO when we need make a progress design

                });
        return getData();
    }

    public LiveData<Cash> postLogoTLow(Uri uri) {
        pathFile(uri).putFile(uri)
                .addOnSuccessListener( task -> {
                    task.getMetadata().getReference().getDownloadUrl().addOnSuccessListener( uri1 -> {
                        addUserImage(uri1);
                        getCash().setError(false);
                        getCash().setMessage(uri1.toString());
                        getData().setValue(getCash());
                    }).addOnFailureListener(e -> {
                        getCash().setError(true);
                        getCash().setMessage(e.getMessage());
                        getData().setValue(getCash());
                    });
                })
                .addOnFailureListener( e -> {
                    getCash().setError(true);
                    getCash().setMessage(e.getMessage());
                    getData().setValue(getCash());
                })
                .addOnProgressListener( snapshot -> {
                    //TODO when we need make a progress design
                });
        return getData();
    }


}
