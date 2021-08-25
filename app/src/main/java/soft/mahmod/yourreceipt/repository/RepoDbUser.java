package soft.mahmod.yourreceipt.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import soft.mahmod.yourreceipt.model.User;

public class RepoDbUser {
    private static final String TAG = "RepoDbUser";
    private DatabaseReference reference;
    private MutableLiveData<User> data;

    public RepoDbUser(@NonNull Application application) {
        reference = FirebaseDatabase.getInstance().getReference();
        data = new MutableLiveData<>();
    }

    public void newUser(User model, String path) {
        reference.child(path).setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d(TAG, "newUser: is sign");
                    }
                }).addOnFailureListener(Throwable::printStackTrace);
    }
}
