package soft.mahmod.yourreceipt.view_model.ui;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import soft.mahmod.yourreceipt.conditions.ConditionsSignIn;
import soft.mahmod.yourreceipt.controller.SessionManager;
import soft.mahmod.yourreceipt.view_model.VMSignIn;

public class VMSignInUI {
    private ConditionsSignIn conditionsSignIn;
    private VMSignIn vmSignIn;
    private String email, password;
    private SessionManager manager;

    public VMSignInUI() {

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void iniztialize(ViewModelStoreOwner owner, Application application) {
        conditionsSignIn = new ConditionsSignIn(email, password);
        vmSignIn = new ViewModelProvider(owner, new ViewModelProvider.AndroidViewModelFactory(application)).get(VMSignIn.class);
        manager = SessionManager.getInstance(application);
    }

    public void singIn(LifecycleOwner owner) {
        if (conditionsSignIn.isSignIn()) {
            vmSignIn.signIn(email, password).observe(owner, user -> {
                manager.setUser(user);
                manager.userSignIn(user);
            });
        }
    }
}
