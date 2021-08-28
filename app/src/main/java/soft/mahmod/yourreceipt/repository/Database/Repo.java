package soft.mahmod.yourreceipt.repository.Database;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


import java.util.List;
import java.util.Optional;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.User;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class Repo<T> implements DatabaseUrl, OnRepoInsert<T> {
    private static final String TAG = "Repo<T>";
    private MutableLiveData<T> data;
    private MutableLiveData<Cash> errorData;
    private String path;
    private FirebaseAuth fAuth;
    private DatabaseReference reference;
    private String id;
    private final Cash cash = new Cash();
    private Class<T> tClass;

    public Repo() {
        data = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
        path = "";
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        id = String.valueOf(System.currentTimeMillis());
    }

    public Repo(Class<T> tClass) {
        this.tClass = tClass;
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
                    e.printStackTrace();
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    cash.setError(true);
                    errorData.postValue(cash);
                });
    }

    public synchronized void postUser(User model) {
        reference.child(USER).child(fAuth.getUid()).setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cash.setCode(200);
                        cash.setError(false);
                        cash.setMessage("success");
                        errorData.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    cash.setError(true);
                    errorData.postValue(cash);
                });
    }

    @Override
    public void insertObject(T t) {
        reference.child(getPath()).setValue(t)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cash.setCode(200);
                        cash.setError(false);
                        cash.setMessage("success");
                        errorData.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    cash.setError(true);
                    errorData.postValue(cash);
                });
    }



    @Override
    public void insertValue(T t, String path) {
        getReference().child(path.concat(getfAuth().getUid())).setValue(t)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cash.setCode(200);
                        cash.setError(false);
                        cash.setMessage("success");
                        errorData.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    cash.setError(true);
                    errorData.postValue(cash);
                });
    }

    @Override
    public void insertList(List<T> t) {
        // FIXME: 8/28/2021 i guess list need convert to array
        getReference().child(path.concat(getfAuth().getUid())).setValue(t)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cash.setCode(200);
                        cash.setError(false);
                        cash.setMessage("success");
                        errorData.postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    cash.setError(true);
                    errorData.postValue(cash);
                });
    }


    public DatabaseReference getReference() {
        return reference;
    }

    public Cash getCash() {
        return cash;
    }

    public FirebaseAuth getfAuth() {
        return fAuth;
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
