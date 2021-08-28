package soft.mahmod.yourreceipt.repository.Database;

import androidx.lifecycle.MutableLiveData;


import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Items;
import soft.mahmod.yourreceipt.statics.DatabaseUrl;

public class RepoItem implements DatabaseUrl {
    private RepoReceipt receipt;
    private MutableLiveData<Items> data;

    public RepoItem() {
        data = new MutableLiveData<>();
        receipt = new RepoReceipt();
        receipt.path = ITEMS + receipt.getfAuth().getUid() + "/" + receipt.randomId;
    }

    public void createItem(Items model) {
        Cash cash = new Cash();
        model.setItemId(receipt.randomId);
        receipt.getReference().child(receipt.path).setValue(model)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cash.setError(false);
                        cash.setMessage("success");
                        cash.setCode(200);
                        data.postValue(model);
                        receipt.getErrorData().postValue(cash);
                    }
                })
                .addOnFailureListener(e -> {
                    cash.setError(true);
                    cash.setMessage(e.getMessage());
                    cash.setCode(0);
                    receipt.getErrorData().postValue(cash);
                });
    }

    public MutableLiveData<Items> getData() {
        return data;
    }

    public MutableLiveData<Cash> getErrorData() {
        return receipt.getErrorData();
    }
}
