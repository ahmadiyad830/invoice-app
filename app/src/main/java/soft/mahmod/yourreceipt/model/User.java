package soft.mahmod.yourreceipt.model;


import com.google.gson.annotations.SerializedName;

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

    public String getUserId() {
        return userId;
    }

    public User(String message, Boolean error, Integer code, String email,
                String password, String storeName, String phoneNum, String storeAddress, String userId) {
        super(message, error, code);
        this.email = email;
        this.password = password;
        this.storeName = storeName;
        this.phoneNum = phoneNum;
        this.storeAddress = storeAddress;
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User(String message, Boolean error, Integer code, String userId) {
        super(message, error, code);
        this.userId = userId;
    }

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

    public String getPhoneNum() {
        return phoneNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", storeName='" + storeName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", userId='" + userId + '\'' +
                '}';
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
}
