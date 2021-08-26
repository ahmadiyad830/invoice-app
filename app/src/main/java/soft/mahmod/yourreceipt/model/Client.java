package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.Field;

public class Client extends Cash{


    private String email;

    private String phone;

    private String addInfo;

    private String taxRegNo;

    private String address;

    private String storeAddress;

    private String note;

    private String name;

    private List<String> receiptId;


    private String clientId;

    public Client() {

    }

    public Client(String message, boolean error, Integer code) {
        super(message, error, code);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getTaxRegNo() {
        return taxRegNo;
    }

    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @NonNull
    @Override
    public String toString() {
        return "Client{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", addInfo='" + addInfo + '\'' +
                ", taxRegNo='" + taxRegNo + '\'' +
                ", address='" + address + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", note='" + note + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
