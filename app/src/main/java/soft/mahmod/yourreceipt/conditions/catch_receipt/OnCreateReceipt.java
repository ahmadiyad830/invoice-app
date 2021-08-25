package soft.mahmod.yourreceipt.conditions.catch_receipt;

import soft.mahmod.yourreceipt.conditions.Error;

public abstract class OnCreateReceipt extends Error {
    private String subject, name, phone, totalAll, note, type;

    @Override
    public boolean lengthInput(String input) {
        return false;
    }

    public abstract String getName();

    public abstract String getSubject();

    public abstract String getPhone();

    public abstract String getTotalAll();



    public abstract String getNote();
}
