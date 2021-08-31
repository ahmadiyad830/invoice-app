package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import soft.mahmod.yourreceipt.repository.database.RepoProducts;

public class VMProducts extends AndroidViewModel {
    private RepoProducts repoProducts;
    public VMProducts(@NonNull Application application) {
        super(application);
        repoProducts = new RepoProducts(application);
    }

}
