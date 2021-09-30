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

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Client;

public class RepoClient extends Repo<Client> implements OnCompleteListener<Void>, OnFailureListener {
    private Client client = new Client();

    public RepoClient(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Client> postClient(Client model) {
        model.setClientId(getReference().push().getKey());
        getReference().child(CLIENT).child(getfUser().getUid()).child(model.getClientId())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(), this)
                .addOnFailureListener(getApplication().getMainExecutor(), this);
        return getData();
    }

    public LiveData<Client> postClientTLow(Client model) {
        model.setClientId(getReference().push().getKey());
        getReference().child(CLIENT).child(getfUser().getUid()).child(model.getClientId())
                .setValue(model)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Client> putClient(Client model) {
        getReference().child(CLIENT).child(getfUser().getUid()).child(model.getClientId())
                .setValue(model)
                .addOnCompleteListener(getApplication().getMainExecutor(),this)
                .addOnFailureListener(getApplication().getMainExecutor(),this);
        return getData();
    }

    public LiveData<Client> putClientTLow(Client model) {
        getReference().child(CLIENT).child(getfUser().getUid()).child(model.getClientId())
                .setValue(model)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Client> deleteClient(String clientId) {
        getReference().child(CLIENT).child(getfUser().getUid())
                .child(clientId)
                .removeValue()
                .addOnCompleteListener(getApplication().getMainExecutor(),this)
                .addOnFailureListener(getApplication().getMainExecutor(),this);
        return getData();
    }

    public LiveData<Client> deleteClientTLow(String clientId) {
        getReference().child(CLIENT).child(getfUser().getUid())
                .child(clientId)
                .removeValue()
                .addOnCompleteListener(this)
                .addOnFailureListener(this);
        return getData();
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
            getData().setValue(client);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            postError(error.getMessage());
            Client client = new Client();
            client.setError(true);
            client.setMessage(error.getMessage());
            client.setCode(TRY_AGAIN);
        }
    };

    public void clean() {
        getReference().removeEventListener(getClient);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            client.setError(false);
            client.setMessage("success");
            client.setCode(SUCCESS);
            getData().setValue(client);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        postError(e.getLocalizedMessage());
        client.setError(true);
        client.setMessage(e.getMessage());
        client.setCode(TRY_AGAIN);
        getData().setValue(client);
    }
}
