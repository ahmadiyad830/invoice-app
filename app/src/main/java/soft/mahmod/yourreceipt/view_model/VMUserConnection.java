package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.RepoUserConnection;

public class VMUserConnection extends ViewModel {
    private RepoUserConnection connection;

    public VMUserConnection() {
        connection = new RepoUserConnection();
    }

    public LiveData<Cash> userConnection(String email) {
        return connection.userConnection(email);
    }
}
