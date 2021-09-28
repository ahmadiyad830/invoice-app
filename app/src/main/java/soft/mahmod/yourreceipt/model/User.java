package soft.mahmod.yourreceipt.model;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class User extends Cash implements Serializable {

    private String email;
    private String password;
    private String uid;
    private String security;
    private boolean active = false, block = false;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String uid, boolean active, boolean block) {
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.active = active;
        this.block = block;
    }

    public User() {

    }

    public User(boolean active) {
        this.active = active;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @NonNull
    @Override
    public String toString() {
        super.toString();
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", uid='" + uid + '\'' +
                ", active=" + active +
                ", block=" + block +
                '}';
    }
}
