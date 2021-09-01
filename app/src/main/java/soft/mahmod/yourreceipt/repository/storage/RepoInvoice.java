package soft.mahmod.yourreceipt.repository.storage;

import android.app.Application;

import soft.mahmod.yourreceipt.model.Cash;

public class RepoInvoice extends Repo<Cash> {
    public RepoInvoice(Application application) {
        super(application);
    }
}
