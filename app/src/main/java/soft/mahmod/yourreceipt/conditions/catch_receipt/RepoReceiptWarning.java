package soft.mahmod.yourreceipt.conditions.catch_receipt;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soft.mahmod.yourreceipt.model.Receipt;

public class RepoReceiptWarning {

    private Map<String, String> warnings = new HashMap<>();
    private MutableLiveData<Map<String, String>> dataWarning;

    public RepoReceiptWarning() {
        dataWarning = new MutableLiveData<>();
    }

    public LiveData<Map<String, String>> getDataWarning() {
        return dataWarning;
    }

    public void setListWarning(Map<String, String> warnings) {
        for (Map.Entry<String, String> pair : warnings.entrySet()) {
            if (pair == null || pair.getValue().isEmpty() || pair.getValue().equals("0") || pair.getValue().equals("0.0")) {
                this.warnings = warnings;
                dataWarning.setValue(warnings);
            }
        }
    }

    public boolean isNull(String filed) {
        return filed == null || filed.isEmpty();
    }


}
