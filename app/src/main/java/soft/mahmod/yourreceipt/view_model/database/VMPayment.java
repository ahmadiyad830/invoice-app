package soft.mahmod.yourreceipt.view_model.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.billing.Payment;
import soft.mahmod.yourreceipt.repository.database.RepoPayment;

public class VMPayment extends AndroidViewModel {
    private RepoPayment repo;
    public VMPayment(@NonNull Application application) {
        super(application);
        repo = new RepoPayment(application);
    }
    public LiveData<Payment> putPaid(String receiptId, int index, boolean isPaid){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
           return repo.putPaid(receiptId, index, isPaid);
        }else {
            return repo.putPaidTLow(receiptId, index, isPaid);
        }
    }

    public LiveData<Cash> deletePayment(String receiptId, int index){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return repo.deletePayment(receiptId, index);
        }else {
            return repo.deletePaymentTLow(receiptId, index);
        }
    }
}

