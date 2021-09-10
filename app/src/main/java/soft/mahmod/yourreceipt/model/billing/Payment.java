package soft.mahmod.yourreceipt.model.billing;

import java.util.ArrayList;
import java.util.List;

public class Payment {
    private List<String> date;
    private List<String> price;
    private List<String> listBayment;
    private TypeBayment typePayment;

    public List<String> listBill() {
        listBayment = new ArrayList<>();
        listBayment.addAll(date);
        listBayment.addAll(price);
        setListBayment(listBayment);
        return listBayment;
    }

    public void setListBayment(List<String> listBayment) {
        this.listBayment = listBayment;
    }

    public List<String> getListBayment() {
        return listBayment;
    }

    public TypeBayment getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(TypeBayment typePayment) {
        this.typePayment = typePayment;
    }
}
