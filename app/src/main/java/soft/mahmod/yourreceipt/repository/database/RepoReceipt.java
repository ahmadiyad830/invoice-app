package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.User;

public class RepoReceipt extends Repo<Receipt>{
    public RepoReceipt(Application application) {
        super(application);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public synchronized LiveData<Cash> postReceipt(Receipt model) {
        model.setReceiptId(getReference().push().getKey());
        getReference().child(RECEIPT).child(getfUser().getUid()).child(model.getReceiptId())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        getCash().setError(false);
                        getCash().setMessage("success");
                        getCash().setCode(SUCCESS);
                        getErrorDate().setValue(getCash());
                    }

                })
                .addOnFailureListener(getApplication().getMainExecutor(), e -> {
                    getCash().setError(true);
                    getCash().setMessage(e.getMessage());
                    getCash().setCode(TRY_AGAIN);
                    getErrorDate().setValue(getCash());
                });
        return getErrorDate();
    }

    public synchronized LiveData<Cash> postClientTLow(Receipt model) {
        model.setClientId(getReference().push().getKey());
        getReference().child(RECEIPT).child(getfUser().getUid()).push()
                .setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getCash().setError(false);
                        getCash().setMessage("success");
                        getCash().setCode(SUCCESS);
                        getErrorDate().setValue(getCash());
                    }

                })
                .addOnFailureListener(e -> {
                    getCash().setError(true);
                    getCash().setMessage(e.getMessage());
                    getCash().setCode(TRY_AGAIN);
                    getErrorDate().setValue(getCash());
                });
        return getErrorDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public synchronized LiveData<Cash> putEditValue(double editValue, String pushKey, String editKey){
        Map<String, Object> result = new HashMap<>();
        result.put(editKey,editValue);
        getReference().child(RECEIPT).child(getfUser().getUid()).child(pushKey)
                .updateChildren(result)
                .addOnSuccessListener(getApplication().getMainExecutor(),unused -> {
                    getCash().setMessage("success");
                    getCash().setCode(200);
                    getCash().setError(false);
                    getErrorDate().setValue(getCash());
                })
                .addOnFailureListener(getApplication().getMainExecutor(),e -> {
                    getCash().setMessage(e.getMessage());
                    getCash().setCode(404);
                    getCash().setError(true);
                    getErrorDate().setValue(getCash());
                });
        return getErrorDate();
    }
    public synchronized LiveData<Cash> putEditValueTLow(double editValue, String pushKey, String editKey){
        Map<String, Object> result = new HashMap<>();
        result.put(editKey,editValue);
        getReference().child(RECEIPT).child(getfUser().getUid()).child(pushKey)
                .updateChildren(result)
                .addOnSuccessListener(unused -> {
                    getCash().setMessage("success");
                    getCash().setCode(200);
                    getCash().setError(false);
                    getErrorDate().setValue(getCash());
                })
                .addOnFailureListener(e -> {
                    getCash().setMessage(e.getMessage());
                    getCash().setCode(404);
                    getCash().setError(true);
                    getErrorDate().setValue(getCash());
                });
        return getErrorDate();
    }


    public synchronized LiveData<Cash> deleteReceipt(Receipt model){
        return getErrorDate();
    }

    public synchronized LiveData<Cash> deleteReceiptTLow(Receipt model){
        return getErrorDate();
    }

    public LiveData<Receipt> getReceipt(String pushKey){
        getReference().child(RECEIPT).child(getfUser().getUid()).child(pushKey)
                .addListenerForSingleValueEvent(getReceipt);
        return getData();
    }

    ValueEventListener  getReceipt = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            User user = new User();
            if (snapshot.exists()) {
                user = snapshot.getValue(User.class);
                user.setError(false);
                user.setMessage("success");
                user.setCode(SUCCESS);
            } else {
                user.setError(true);
                user.setMessage("path not exists");
                user.setCode(PATH_NOT_EXISTS);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            User user = new User();
            user.setError(true);
            user.setMessage(error.getMessage());
            user.setCode(TRY_AGAIN);
        }
    };

    public void clean() {
        getReference().removeEventListener(getReceipt);
    }
}
