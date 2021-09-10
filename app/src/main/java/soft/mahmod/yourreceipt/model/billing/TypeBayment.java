package soft.mahmod.yourreceipt.model.billing;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.util.List;

enum TypeBayment implements Debt, Bayment {
    DEBT,
    PAID,
    BAYMENT;

    @Override
    public void setDate(String date) {
        DEBT.setDate(date);
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public String getDate() {
        return DEBT.getDate();
    }

    @Override
    public void setBayments(List<String> bayments) {
        BAYMENT.setBayments(bayments);
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public List<String> getBayments() {
        return BAYMENT.getBayments();
    }
}
