package soft.mahmod.yourreceipt.repository.storage;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.storage.StorageReference;

import soft.mahmod.yourreceipt.model.Cash;

public class RepoInvoice extends Repo<Cash> {
    public RepoInvoice(Application application) {
        super(application);
    }

    private StorageReference pathFile(Uri uri) {
        return getReference().child(getUid())
                .child("Invoice" + "." + getFileExtension(uri));
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postInvoice(Uri uri) {
        pathFile(uri).putFile(uri)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        getCash().setError(false);
                        getData().setValue(getCash());
                    }
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

    public LiveData<Cash> postInvoiceTLow(Uri uri) {
        pathFile(uri).putFile(uri)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getCash().setError(false);
                        getData().setValue(getCash());
                    }
                })
                .addOnFailureListener(e -> {
                    getCash().setError(true);
                    getCash().setMessage(e.getMessage());
                    getData().setValue(getCash());
                })
                .addOnProgressListener(snapshot -> {
                    //TODO when we need make a progress design
                });
        return getData();
    }
}
