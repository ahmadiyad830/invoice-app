package soft.mahmod.yourreceipt.view_model.create_receipt;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Client;
import soft.mahmod.yourreceipt.repository.create.RepoClient;

public class VMClient extends ViewModel{
    private RepoClient repoClient;
    private MutableLiveData<Client> data;
    private MutableLiveData<Cash> errorData;

    public VMClient() {
        repoClient = new RepoClient();
        data = repoClient.getData();
        errorData = repoClient.getErrorData();
    }
    public void postClient(Client model){
        repoClient.postData(model);
    }

    public MutableLiveData<Cash> getErrorData() {
        return errorData;
    }

    public MutableLiveData<Client> getData() {
        return data;
    }
}
