package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.repository.RepoItemsByEmail;

public class VMItemByEmail extends ViewModel {
    private RepoItemsByEmail repoItemsByEmail;

    public VMItemByEmail() {
        repoItemsByEmail = new RepoItemsByEmail();
    }
    public LiveData<List<Items>> itemByEmail(String email){
        return repoItemsByEmail.itemByEmail(email);
    }
}
