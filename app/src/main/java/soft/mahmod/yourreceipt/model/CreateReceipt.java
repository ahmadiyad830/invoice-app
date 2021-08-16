package soft.mahmod.yourreceipt.model;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.List;

public class CreateReceipt implements Serializable {
    private List<Client> listClient;
    private List<Products> listProducts;
    private String subject;
    private boolean typeReceipt;
    private boolean hasPrint;
    private boolean addLogo;
    private boolean checked;
    private String discount;
    private String note;

    public CreateReceipt() {

    }


    public MutableLiveData<CreateReceipt> data() {
        return new MutableLiveData<>();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Client> getListClient() {
        return listClient;
    }

    public void setListClient(List<Client> listClient) {
        this.listClient = listClient;
    }

    public List<Products> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Products> listProducts) {
        this.listProducts = listProducts;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isTypeReceipt() {
        return typeReceipt;
    }

    public void setTypeReceipt(boolean typeReceipt) {
        this.typeReceipt = typeReceipt;
    }

    public boolean isHasPrint() {
        return hasPrint;
    }

    public void setHasPrint(boolean hasPrint) {
        this.hasPrint = hasPrint;
    }

    public boolean isAddLogo() {
        return addLogo;
    }

    public void setAddLogo(boolean addLogo) {
        this.addLogo = addLogo;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
