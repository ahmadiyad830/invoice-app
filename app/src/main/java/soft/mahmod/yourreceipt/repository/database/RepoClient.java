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
import soft.mahmod.yourreceipt.model.Client;

public class RepoClient extends Repo<Client> {


    public RepoClient(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postClient(Client model) {
        model.setClientId(getReference().push().getKey());
        getReference().child(CLIENT).child(getfUser().getUid()).child(model.getClientId())
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

    public LiveData<Cash> postClientTLow(Client model) {
        model.setClientId(getReference().push().getKey());
        getReference().child(CLIENT).child(getfUser().getUid()).child(model.getClientId())
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
    public LiveData<Cash> putClient(Client model) {
        getReference().child(CLIENT).child(getfUser().getUid()).child(model.getClientId())
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

    public LiveData<Cash> putClientTLow(Client model) {
        getReference().child(CLIENT).child(getfUser().getUid()).child(model.getClientId())
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

    public LiveData<Cash> deleteClient(Client model) {

        return getErrorDate();
    }

    public LiveData<Cash> deleteClientTLow(Client model) {
        return getErrorDate();
    }

    public LiveData<Client> getClient(String pushKey) {
        getReference().child(CLIENT).child(getfUser().getUid()).child(pushKey)
                .addListenerForSingleValueEvent(getClient);
        return getData();
    }

    ValueEventListener getClient = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Client client = new Client();
            if (snapshot.exists()) {
                client = snapshot.getValue(Client.class);
                client.setError(false);
                client.setMessage("success");
                client.setCode(SUCCESS);

            } else {
                client.setError(true);
                client.setMessage("path not exists");
                client.setCode(PATH_NOT_EXISTS);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Client client = new Client();
            client.setError(true);
            client.setMessage(error.getMessage());
            client.setCode(TRY_AGAIN);
        }
    };

    public void clean() {
        getReference().removeEventListener(getClient);
    }
}
