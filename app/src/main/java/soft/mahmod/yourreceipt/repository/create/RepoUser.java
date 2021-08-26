package soft.mahmod.yourreceipt.repository.create;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import soft.mahmod.yourreceipt.model.User;

public class RepoUser extends Repo<User>{
    @Override
    public synchronized void insert(User user) {
        super.insert(user);
    }

    public void readData() {
        setPath(USER+getfAuth().getUid());
        getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(getPath()).exists()){
                    getData().setValue(snapshot.child(getPath()).getValue(User.class));
                    getCash().setCode(200);
                    getCash().setError(false);
                    getCash().setMessage("success");
                }else {
                   getCash().setCode(404);
                   getCash().setError(true);
                   getCash().setMessage("path not exists");
                }
                getErrorData().postValue(getCash());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.toException().printStackTrace();
                 getCash().setCode(0);
                 getCash().setError(true);
                 getCash().setMessage(error.getMessage());
                getErrorData().postValue(getCash());
            }
        });
    }
}
