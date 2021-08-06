package soft.mahmod.yourreceipt.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.repository.RepoAddLogo;

public class VMAddLogo extends ViewModel {
    private RepoAddLogo repoAddLogo;

    public VMAddLogo() {
        repoAddLogo = new RepoAddLogo();
    }

    public LiveData<Cash> addLogo(String path, String email) {
        return repoAddLogo.addLogo(path, email);
    }
}
