package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.repository.database.RepoPayment;

public class VMPayment extends AndroidViewModel {
    private RepoPayment repoPayment;
    public VMPayment(@NonNull Application application) {
        super(application);
        repoPayment = new RepoPayment(application);
    }
    public LiveData<Payment> putPaid(String receiptId, int index, boolean isPaid){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
           return repoPayment.putPaid(receiptId, index, isPaid);
        }else {
            return repoPayment.putPaidTLow(receiptId, index, isPaid);
        }
    }
}
