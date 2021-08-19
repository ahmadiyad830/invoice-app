package soft.mahmod.yourreceipt.model;


import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class User extends Cash {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("phone_num")
    private String phoneNum;
    @SerializedName("store_address")
    private String storeAddress;
    @SerializedName("user_id")
    private String userId;

    public User(String message, boolean error, Integer code) {
        super(message, error, code);
    }

    public String getUserId() {
        return userId;
    }

    public User(String email,
                String password, String storeName, String phoneNum, String storeAddress, String userId) {
        this.email = email;
        this.password = password;
        this.storeName = storeName;
        this.phoneNum = phoneNum;
        this.storeAddress = storeAddress;
        this.userId = userId;
    }
    // the constractor use in sign up
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String storeName, String phoneNum, String storeAddress ) {
        this.email = email;
        this.password = password;
        this.storeName = storeName;
        this.phoneNum = phoneNum;
        this.storeAddress = storeAddress;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User( String userId) {
        this.userId = userId;
    }

    public User() {

    }

    public User(String message, Boolean error, Integer code,
                String email, String password, String storeName,
                String phoneNum, String storeAddress, String userId) {
        super(message, error, code);
        this.email = email;
        this.password = password;
        this.storeName = storeName;
        this.phoneNum = phoneNum;
        this.storeAddress = storeAddress;
        this.userId = userId;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", storeName='" + storeName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", userId='" + userId + '\'' +
                '}'+super.toString();
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    @Override
    public void cleanUser() {
        super.cleanUser();
        this.email = null;
        this.password = null;
    }
}
