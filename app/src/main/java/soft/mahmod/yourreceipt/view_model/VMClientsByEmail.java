package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.repository.RepoClientsByEmail;

public class VMClientsByEmail extends ViewModel {
    private RepoClientsByEmail clientsByEmail;

    public VMClientsByEmail() {
        clientsByEmail = new RepoClientsByEmail();
    }
    public LiveData<List<Client>> clientsByEmail(String email){
        return clientsByEmail.clientsByEmail(email);
    }

}
