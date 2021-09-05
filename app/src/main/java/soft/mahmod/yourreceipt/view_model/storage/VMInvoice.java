package soft.mahmod.yourreceipt.view_model.storage;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.storage.RepoInvoice;

public class VMInvoice extends AndroidViewModel {
    private RepoInvoice repoInvoice;
    public VMInvoice(@NonNull Application application) {
        super(application);
        repoInvoice = new RepoInvoice(application);
    }
    public LiveData<Cash> postInvoice(Uri uri){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repoInvoice.postInvoice(uri);
        }else return repoInvoice.postInvoiceTLow(uri);
    }
    public double getProgress(){
        return repoInvoice.getProgress();
    }
}
