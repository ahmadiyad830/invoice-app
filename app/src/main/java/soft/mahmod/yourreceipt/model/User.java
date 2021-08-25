package soft.mahmod.yourreceipt.model;



import java.io.Serializable;

public  class User  implements Serializable{

    private String email;
    private String password;
    private String uid;




    public User(String email, String password, String uid) {
        this.email = email;
        this.password = password;
        this.uid = uid;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

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


}
