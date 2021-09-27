package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class Repo<T> implements DatabaseUrl {
    private final DatabaseReference reference;
    private final FirebaseUser fUser;
    private final MutableLiveData<Cash> errorDate;
    private final MutableLiveData<T> data;
    private final Application application;
    private final Cash cash;
    private T t ;
    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Repo(Application application) {
        this.application = application;
        cash = new Cash();
        errorDate = new MutableLiveData<>();
        data = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }


    public LiveData<Integer> sizeReference(String ref1) {
        MutableLiveData<Integer> dataSize = new MutableLiveData<>();
        reference.child(ref1)
                .child(fUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int size = ((int) snapshot.getChildrenCount());
                        dataSize.setValue(size);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return dataSize;
    }

    public void postError(String error) {
        getReference().child(ERROR)
                .child(fUser.getUid())
                .setValue(error);
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public FirebaseUser getfUser() {
        return fUser;
    }

    public MutableLiveData<Cash> getErrorDate() {
        return errorDate;
    }

    public MutableLiveData<T> getData() {
        return data;
    }

    public Application getApplication() {
        return application;
    }

    public Cash getCash() {
        return cash;
    }

    public String getResources(@StringRes int res) {
        return application.getResources().getString(res);
    }
}
