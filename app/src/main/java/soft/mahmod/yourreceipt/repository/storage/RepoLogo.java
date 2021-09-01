package soft.mahmod.yourreceipt.repository.storage;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;

public class RepoLogo extends Repo<Cash> {
    public RepoLogo(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postLogo(Uri uri) {
        getReference().putFile(uri)
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

    public LiveData<Cash> postLogoTLow(Uri uri) {
        getReference().putFile(uri)
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
