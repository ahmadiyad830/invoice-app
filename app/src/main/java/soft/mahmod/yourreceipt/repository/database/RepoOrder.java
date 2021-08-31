package soft.mahmod.yourreceipt.repository.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Orders;

public class RepoOrder extends Repo<Orders>{
    public RepoOrder(Application application) {
        super(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public LiveData<Cash> postOrders(Orders model) {
        model.setOrderId(getReference().push().getKey());
        getReference().child(ORDER).child(getfUser().getUid()).child(model.getOrderId())
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
    public LiveData<Cash> postOrdersTLow(Orders model) {
        model.setOrderId(getReference().push().getKey());
        getReference().child(ORDER).child(getfUser().getUid()).child(model.getOrderId())
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
    public LiveData<Cash> putOrders(Orders model){
        return getErrorDate();
    }
    public LiveData<Cash> putOrdersTLow(Orders model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteOrders(Orders model){
        return getErrorDate();
    }
    public LiveData<Cash> deleteOrdersTLow(Orders model){
        return getErrorDate();
    }
    public LiveData<Orders> getOrders(){
        return getData();
    }

}
