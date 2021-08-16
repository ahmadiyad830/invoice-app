package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.repository.RepoCreateClient;

public class VMCreateClient extends ViewModel {
    private RepoCreateClient createClient;

    public VMCreateClient() {
        createClient = new RepoCreateClient();
    }
    public LiveData<Cash> createClient(Client model){
        return createClient.createClient(model);
    }
}
