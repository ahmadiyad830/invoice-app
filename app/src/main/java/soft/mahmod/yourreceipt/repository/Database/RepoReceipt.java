package soft.mahmod.yourreceipt.repository.Database;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class RepoReceipt implements DatabaseUrl {
    private static final String TAG = "RepoReceipt";
    private FirebaseAuth fAuth;
    private DatabaseReference reference;
    private MutableLiveData<Receipt> data;
    private MutableLiveData<Cash> errorData;
    public String path;
    public String randomId = String.valueOf(System.currentTimeMillis());

    public FirebaseAuth getfAuth() {
        return fAuth;
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public RepoReceipt() {
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        data = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
        path = ALL_RECEIPT + fAuth.getUid() + "/" + randomId;
    }

    public void createReceipt(Receipt model) {
        String randomId = String.valueOf(System.currentTimeMillis());
        model.setReceiptId(randomId);
        Cash cash = new Cash();
        reference.child(path)
                .setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cash.setError(!task.isSuccessful());
                        errorData.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setCode(400);
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
