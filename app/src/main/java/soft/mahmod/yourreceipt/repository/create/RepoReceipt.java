package soft.mahmod.yourreceipt.repository.create;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class RepoReceipt implements DatabaseUrl {
    private FirebaseAuth fAuth;
    private DatabaseReference reference;
    private MutableLiveData<Receipt> data;
    private MutableLiveData<Cash> errorData;

    public RepoReceipt() {
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        data = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
    }

    public void setReceipt(Receipt model) {
        Cash cash = new Cash();
        reference.child(ALL_RECEIPT+fAuth.getUid()).push()
                .setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cash.setError(!task.isSuccessful());
                        errorData.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setCode(200);
                    cash.setMessage(e.getMessage());
                    cash.setError(true);
                    errorData.postValue(cash);
                });

    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }

    public MutableLiveData<Receipt> getData() {
        return data;
    }
}
