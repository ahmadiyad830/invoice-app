package soft.mahmod.yourreceipt.conditions.catch_registration;

import android.util.Log;

public class ConditionsSignUp extends OnErrorRegistration {
    private static final String TAG = "ConditionsSignUp";
    private String email, pass1, pass2;

    public ConditionsSignUp(String email, String pass1, String pass2) {
        this.email = email;
        this.pass1 = pass1;
        this.pass2 = pass2;
        isEmpty();
        passwordConfig();
        lengthPassword();
        validEmail();
    }

    public ConditionsSignUp(String email, String pass1) {
        this.email = email;
        this.pass1 = pass1;
    }

    private ConditionsSignUp() {

    }

    public void passwordConfig() {
        if (!passwordConfig(pass1, pass2)) {
            setMessage("config your password");
            setError(true);
            Log.d(TAG, "passwordConfig: ");
        }
    }

    public void lengthPassword() {
        if (!lengthInput(pass1)) {
            setMessage("config your password");
            setError(true);
        }
    }

    public void isEmpty() {
        if (!isEmpty(email)) {
            setMessage("empty email");
            setError(true);
        }
        if (!isEmpty(pass1)) {
            setMessage("empty password");
            setError(true);
        }
    }

    public void validEmail() {
        if (!validate(email)){
            setMessage("invalid email");
            setError(true);
        }
    }

    @Override
    public boolean getError() {
        return super.getError();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
