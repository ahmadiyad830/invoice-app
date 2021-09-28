package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Store extends Cash implements Serializable {
    private String name, address,  email;
    private int phone;
    private String storeId;
    private String logo;
    public Store(String name, String address, String address2, int phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
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
        super.toString();
        return "Store{" +
                "name='" + name + '\'' +
                ", address1='" + address + '\'' +
                ", numberPhone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
