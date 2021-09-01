package soft.mahmod.yourreceipt.view_model.storage;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.storage.RepoLogo;

public class VMLogo extends AndroidViewModel {
    private RepoLogo repoLogo;

    public VMLogo(@NonNull Application application) {
        super(application);
        repoLogo = new RepoLogo(application);
    }

    public LiveData<Cash> postLogo(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repoLogo.postLogo(uri);
        } else return repoLogo.postLogoTLow(uri);
    }
}
