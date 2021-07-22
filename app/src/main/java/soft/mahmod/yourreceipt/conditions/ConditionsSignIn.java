package soft.mahmod.yourreceipt.conditions;

import android.util.Patterns;

import java.util.regex.Pattern;

public class ConditionsSignIn {
    private String email, password,error;


    public ConditionsSignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isEmail() {
        if (email.isEmpty()){
            error = "empty email feild";
            return true;
        }
        return false;
    }

    public boolean isPassword() {
        if (password.isEmpty()){
            error = "empty password feild";
            return true;
        }
        return false;
    }

    public boolean isSignIn() {

        return !isEmail() && !isPassword();
    }
    public String error (){
        return error;
    }
}
