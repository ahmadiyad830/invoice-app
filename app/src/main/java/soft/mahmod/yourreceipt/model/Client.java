package soft.mahmod.yourreceipt.model;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class Client extends Cash{


    @SerializedName("client_email")
    private String email;

    @SerializedName("phone_number")
    private String phone;

    @SerializedName("additional_information")
    private String addInfo;

    @SerializedName("tax_reg_no")
    private String taxRegNo;


    @SerializedName("address")
    private String address;

    @SerializedName("store_address")
    private String storeAddress;

    @SerializedName("note")
    private String note;

    @SerializedName("client_name")
    private String name;

    @SerializedName("client_user_id")
    private String userId;

    public Client() {

    }

    public Client(String message, boolean error, Integer code) {
        super(message, error, code);
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
                ", userId='" + userId + '\'' +
                '}';
    }
}
