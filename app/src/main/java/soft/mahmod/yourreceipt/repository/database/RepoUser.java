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

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.User;

public class RepoUser extends Repo<User> implements OnCompleteListener<Void>, OnFailureListener {
    private User user = new User();

    public RepoUser(Application application) {
        super(application);
    }
//    write
    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<User> postUser(User model) {
        getReference().child(USER).child(model.getUid())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(), this)
                .addOnFailureListener(getApplication().getMainExecutor(), this);
        return getData();
    }
    public LiveData<User> postUserTLow(User model) {
        getReference().child(USER).child(model.getUid())
                .setValue(model)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
    }
    public LiveData<User> putUser(User model){
        return getData();
    }
    public LiveData<User> putUserTLow(User model){
        return getData();
    }
    public LiveData<User> deleteUser(User model){
        return getData();
    }
    public LiveData<User> deleteUserTLow(User model){
        return getData();
    }
    //    read
    public LiveData<User> getUser(){
        getReference().child(USER).child(getfUser().getUid())
                .addValueEventListener(new ValueEventListener() {
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
                            user.setCode(PATH_NOT_EXISTS);
                            user.setMessage(getResources(R.string.not_exists));
                        }
                        getData().setValue(user);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        postError(error.getMessage());
                        User user = new User();
                        user.setError(true);
                        user.setMessage(error.getMessage());
                        user.setCode(error.getCode());
                        getData().setValue(user);
                    }
                });
        return getData();
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            user.setError(false);
            user.setMessage("success");
            user.setCode(SUCCESS);
            getData().setValue(user);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        postError(e.getLocalizedMessage());
        user.setError(true);
        user.setMessage(e.getMessage());
        user.setCode(TRY_AGAIN);
        getData().setValue(user);
    }
}
