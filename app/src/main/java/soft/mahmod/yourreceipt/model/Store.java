package soft.mahmod.yourreceipt.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.io.Serializable;

public class Store extends Cash implements Serializable {
    private String name, address1, address2, email;
    private int numberPhone;
    private String storeId;
    private String logo;
    public Store(String name, String address1, String address2, int numberPhone, String email) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    public Store() {

    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
