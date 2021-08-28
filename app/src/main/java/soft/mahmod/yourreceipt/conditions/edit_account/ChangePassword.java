package soft.mahmod.yourreceipt.conditions.edit_account;

import android.text.TextUtils;

public class ChangePassword implements OnChangePassword {
    private String error = "";

    @Override
    public boolean changePassword(String pass1, String pass2) {
        if (pass1 == null || pass2 == null) {
            error = "null password";
            return true;
        } else if (pass1.isEmpty() || pass2.isEmpty()) {
            error = "empty password";
            return true;
        } else if (pass1.equals(pass2)) {
            error = "old password equal new password";
            return true;
        } else if (pass2.length() < 6) {
            error = "password length is less then 6";
            return true;
        }
        return false;
    }

    @Override
    public String errorChangePassword() {
        return error;
    }


}
