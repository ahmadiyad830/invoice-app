package soft.mahmod.yourreceipt.conditions.edit_account;

public interface OnChangePassword {
    boolean changePassword(String pass1, String pass2);

    String errorChangePassword();

}
