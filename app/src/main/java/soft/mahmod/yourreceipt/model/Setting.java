package soft.mahmod.yourreceipt.model;

public class Setting {
    private boolean isChangPassword = false;
    private boolean isEditAccount = false;

    public Setting() {

    }

    public boolean isChangPassword() {
        return isChangPassword;
    }

    public void setChangPassword(boolean changPassword) {
        isChangPassword = changPassword;
    }

    public boolean isEditAccount() {
        return isEditAccount;
    }

    public void setEditAccount(boolean editAccount) {
        isEditAccount = editAccount;
    }
}
