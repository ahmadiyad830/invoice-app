package soft.mahmod.yourreceipt.model;


import com.google.gson.annotations.SerializedName;

public class User extends Cash {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("phone_num")
    private String phoneName;
    @SerializedName("store_address")
    private String storeAddress;

    public User(String message, Boolean error, Integer code) {
        super(message, error, code);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
}
