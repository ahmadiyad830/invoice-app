package soft.mahmod.yourreceipt.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.Field;

public class Client extends Cash{


    private String email;

    private int phone;

    private String addInfo;

    private double taxRegNo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public double getTaxRegNo() {
        return taxRegNo;
    }

    public void setTaxRegNo(double taxRegNo) {
        this.taxRegNo = taxRegNo;
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

    public List<String> getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(List<String> receiptId) {
        this.receiptId = receiptId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
