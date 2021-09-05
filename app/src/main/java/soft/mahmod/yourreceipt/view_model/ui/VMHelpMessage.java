package soft.mahmod.yourreceipt.view_model.ui;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import soft.mahmod.yourreceipt.repository.ui.HelpMessage;

public class VMHelpMessage  {
    private HelpMessage helpMessage;
    public VMHelpMessage(@NonNull Context application) {
        helpMessage = new HelpMessage(application);
    }
    public void setHelpMessage(String message){
        helpMessage.setMessage(message);
        helpMessage.showDialog();
    }
}
