package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.billing.Payment;

public class RepoPayment extends Repo<Payment> implements OnCompleteListener<Void>, OnFailureListener {
    private static final String TAG = "RepoPayment";
    private Payment payment = new Payment();
    public RepoPayment(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postPayment(Payment model) {
        return getErrorDate();
    }

    public LiveData<Cash> postPaymentTLow(Payment model) {
        return getErrorDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Payment> putPaid(String receiptId, int index, boolean isPaid) {
        getReference()
                .child(RECEIPT)
                .child(getfUser().getUid())
                .child(receiptId)
                .child(PAYMENT)
                .child(LIST_PAYMENT)
                .child(String.valueOf(index))
                .child(PAID)
                .setValue(isPaid)
                .addOnCompleteListener(getApplication().getMainExecutor(), this)
                .addOnFailureListener(getApplication().getMainExecutor(),this);
        return getData();
    }

    public LiveData<Payment> putPaidTLow(String receiptId, int index, boolean isPaid) {
        getReference()
                .child(RECEIPT)
                .child(getfUser().getUid())
                .child(receiptId)
                .child(PAYMENT)
                .child(LIST_PAYMENT)
                .child(String.valueOf(index))
                .child(PAID)
                .setValue(isPaid)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> deletePayment(String receiptId, int index) {
        getReference()
                .child(RECEIPT)
                .child(getfUser().getUid())
                .child(receiptId)
                .child(PAYMENT)
                .child(LIST_PAYMENT)
                .child(String.valueOf(index))
                .removeValue()
                .addOnCompleteListener(getApplication().getMainExecutor(), this)
                .addOnFailureListener(getApplication().getMainExecutor(), this);
        return getErrorDate();
    }

    public LiveData<Cash> deletePaymentTLow(String receiptId, int index) {
        getReference()
                .child(RECEIPT)
                .child(getfUser().getUid())
                .child(receiptId)
                .child(PAYMENT)
                .child(LIST_PAYMENT)
                .child(String.valueOf(index))
                .removeValue()
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getErrorDate();
    }

    public LiveData<Payment> getPayment() {

        return getData();
    }

    ValueEventListener getReceipt = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Iterable<DataSnapshot> children = snapshot.child(RECEIPT).child(getfUser().getUid()).getChildren();
            while (children.iterator().hasNext()) {
                String type = children.iterator().next().child(PAYMENT).child(TYPE_PAYMENT).getValue(String.class);
                Log.d(TAG, "onDataChange: " + type);
                children.iterator().next().child(PAYMENT).child(LIST_PAYMENT).getValue(Payment.class);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            postError(error.getMessage());
        }
    };

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            payment.setError(false);
            payment.setCode(SUCCESS);
            payment.setMessage("success");
            getData().setValue(payment);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        postError(e.getLocalizedMessage());
        payment.setError(true);
        payment.setCode(TRY_AGAIN);
        payment.setMessage(e.getLocalizedMessage());
        getData().setValue(payment);
    }
}
