package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.User;

public class RepoUser extends Repo<User> {

    public RepoUser(Application application) {
        super(application);
    }
//    write
    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postUser(User model) {
        getReference().child(USER).child(getfUser().getUid())
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
    public LiveData<Cash> postUserTLow(User model) {
        getReference().child(USER).child(getfUser().getUid())
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
    public LiveData<Cash> putUser(User model){
        return getErrorDate();
    }
    public LiveData<Cash> putUserTLow(User model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteUser(User model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteUserTLow(User model){
        return getErrorDate();
    }
    //    read
    public LiveData<User> getUser(){
        getReference().child(USER).child(getfUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        user.setError(false);
                        user.setMessage("success");
                        user.setCode(SUCCESS);
                        getData().setValue(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        User user = new User();
                        user.setError(true);
                        user.setMessage(error.getMessage());
                        user.setCode(TRY_AGAIN);
                        getData().setValue(user);
                    }
                });
        return getData();
    }

}
