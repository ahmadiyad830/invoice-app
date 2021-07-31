package soft.mahmod.yourreceipt.update.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RefreshLiveData<T extends Object> extends MutableLiveData<T> {
    public interface RefreshAction<T> {
         interface Callback<T> {
            void onDataLoaded(T t);
        }

        void loadData(Callback<T> callback);
    }

    private final RefreshAction<T> refreshAction;
    private final RefreshAction.Callback<T> callback = this::postValue;

    public RefreshLiveData(RefreshAction<T> refreshAction) {
        this.refreshAction = refreshAction;
    }

    public final void refresh() {
        refreshAction.loadData(callback);
    }
}
