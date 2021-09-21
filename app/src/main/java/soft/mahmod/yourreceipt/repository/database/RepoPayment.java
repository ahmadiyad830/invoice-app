package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.billing.Payment;

public class RepoPayment extends Repo<Payment>{
    private static final String TAG = "RepoPayment";

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
        Payment payment = new Payment();
        getReference()
                .child(RECEIPT)
                .child(getfUser().getUid())
                .child(receiptId)
                .child(PAYMENT)
                .child(LIST_PAYMENT)
                .child(String.valueOf(index))
                .child(PAID)
                .setValue(isPaid)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        payment.setError(false);
                        payment.setCode(SUCCESS);
                        payment.setMessage("success");
                        getData().setValue(payment);
                    }
                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    payment.setError(true);
                    payment.setCode(TRY_AGAIN);
                    payment.setMessage(e.getLocalizedMessage());
                });
        return getData();
    }

    public LiveData<Payment> putPaidTLow(String receiptId, int index, boolean isPaid) {
        Payment payment = new Payment();
        getReference()
                .child(RECEIPT)
                .child(getfUser().getUid())
                .child(receiptId)
                .child(PAYMENT)
                .child(LIST_PAYMENT)
                .child(String.valueOf(index))
                .child(PAID)
                .setValue(isPaid)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        payment.setError(false);
                        payment.setCode(SUCCESS);
                        payment.setMessage("success");
                        getData().setValue(payment);
                    }
                })
                .addOnFailureListener( e -> {
                    payment.setError(true);
                    payment.setCode(TRY_AGAIN);
                    payment.setMessage(e.getLocalizedMessage());
                });
        return getData();
    }

    public LiveData<Cash> deletePayment(Payment model) {
        return getErrorDate();
    }

    public LiveData<Cash> deletePaymentTLow(Payment model) {
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

        }
    };
}
