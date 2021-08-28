package soft.mahmod.yourreceipt.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.io.Serializable;
public class Store extends Cash implements Serializable {
    private String name, address1, address2, numberPhone, email;

    public Store(String name, String address1, String address2, String numberPhone, String email) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.numberPhone = numberPhone;
        this.email = email;
    }

    public Store() {

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

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
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
