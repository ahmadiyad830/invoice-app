package soft.mahmod.yourreceipt.repository.Database;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import soft.mahmod.yourreceipt.model.Store;

public class RepoStore extends Repo<Store> implements OnRepoRead<Store> {
    private static final String TAG = "RepoStore";

    public void insertStore(Store model){
        insertValue(model,STORE);
    }

    public void updateData(Map<String,Object> updateValue){
        getReference().updateChildren(updateValue, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
    }
    @Override
    public MutableLiveData<Store> getData() {
        return super.getData();
    }

    @Override
    public void readObject() {
        setPath(STORE+getfAuth().getUid());
        getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(getPath()).exists()){
                    getData().setValue(snapshot.child(getPath()).getValue(Store.class));
                    getCash().setCode(SUCCESS);
                    getCash().setError(false);
                    getCash().setMessage("success");
                }else {
                    getCash().setCode(NOT_FOUND);
                    getCash().setError(true);
                    getCash().setMessage("path not exists");
                }
                getErrorData().setValue(getCash());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                getCash().setCode(NOT_FOUND);
                getCash().setError(true);
                getCash().setMessage(error.getMessage());
                getErrorData().setValue(getCash());
            }
        });
    }

    @Override
    public void readValue(Store store) {

    }
}
