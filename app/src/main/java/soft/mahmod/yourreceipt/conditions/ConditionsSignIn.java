package soft.mahmod.yourreceipt.conditions;

import android.util.Patterns;

import java.util.regex.Pattern;

public class ConditionsSignIn {
    private String email,password;

    public ConditionsSignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private boolean isEmail(){
        if (getEmail().isEmpty())
            return true;
        else return getEmail() == null;
    }
    private boolean isPassword(){
        if (getEmail().isEmpty())
            return true;
        else return getEmail() == null;
    }
}
