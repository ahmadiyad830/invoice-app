package soft.mahmod.yourreceipt.repository.create;



import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class Repo<T> implements DatabaseUrl {
    private static final String TAG = "Repo<T>";
    private MutableLiveData<T> data;
    private MutableLiveData<Cash> errorData;
    private String path;
    private FirebaseAuth fAuth;
    private DatabaseReference reference;
    private String id;
    private final Cash cash = new Cash();

    public Repo() {
        data = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
        path = "";
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        id = String.valueOf(System.currentTimeMillis());
    }

    public synchronized void postData(T model) {
        reference.child(getPath()).child(fAuth.getUid()).child(id).setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cash.setCode(200);
                        cash.setError(false);
                        cash.setMessage("success");
                        errorData.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    cash.setError(true);
                });
    }


    public String getPath() {
        return path;
    }

    public String getId() {
        return id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }

    public MutableLiveData<T> getData() {
        return data;
    }
}
