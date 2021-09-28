package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;
import soft.mahmod.yourreceipt.model.User;

public class RepoReceipt extends Repo<Receipt> implements OnCompleteListener<Void>, OnFailureListener {
    private Receipt receipt = new Receipt();
    public RepoReceipt(Application application) {
        super(application);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public synchronized LiveData<Receipt> postReceipt(Receipt model) {
        model.setReceiptId(getReference().push().getKey());
        getReference().child(RECEIPT).child(getfUser().getUid()).child(model.getReceiptId())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(), this)
                .addOnFailureListener(getApplication().getMainExecutor(),this);
        return getData();
    }

    public synchronized LiveData<Receipt> postClientTLow(Receipt model) {
        model.setClientId(getReference().push().getKey());
        getReference().child(RECEIPT).child(getfUser().getUid()).push()
                .setValue(model)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public synchronized LiveData<Receipt> putEditValue(double editValue, String pushKey, String editKey){
        Map<String, Object> result = new HashMap<>();
        result.put(editKey,editValue);
        getReference().child(RECEIPT).child(getfUser().getUid()).child(pushKey)
                .updateChildren(result)
                .addOnCompleteListener(getApplication().getMainExecutor(),this)
                .addOnFailureListener(getApplication().getMainExecutor(),this);
        return getData();
    }
    public synchronized LiveData<Receipt> putEditValueTLow(double editValue, String pushKey, String editKey){
        Map<String, Object> result = new HashMap<>();
        result.put(editKey,editValue);
        getReference().child(RECEIPT).child(getfUser().getUid()).child(pushKey)
                .updateChildren(result)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
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

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            receipt.setError(false);
            receipt.setMessage("success");
            receipt.setCode(SUCCESS);
            getData().setValue(receipt);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        postError(e.getLocalizedMessage());
        receipt.setError(true);
        receipt.setMessage(e.getMessage());
        receipt.setCode(TRY_AGAIN);
        getData().setValue(receipt);
    }
}
