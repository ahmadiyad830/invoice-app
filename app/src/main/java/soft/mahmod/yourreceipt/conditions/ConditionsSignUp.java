package soft.mahmod.yourreceipt.conditions;

public class ConditionsSignUp {
    private String email, password, error, phone, name, address;


    public ConditionsSignUp(String email, String password, String phone, String name, String address) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.address = address;
    }

    public ConditionsSignUp(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isEmail() {
        if (email.isEmpty()) {
            error = "empty email feild";
            return true;
        }
        return false;
    }

    public boolean isPassword() {
        if (password.isEmpty()) {
            error = "empty password feild";
            return true;
        }
        return false;
    }

    public boolean isPhone() {
        if (phone.isEmpty()) {
            error = "empty Phone feild";
            return true;
        }
        return false;
    }

    public boolean isName() {
        if (name.isEmpty()) {
            error = "empty Name feild";
            return true;
        }
        return false;
    }

    public boolean isAddress() {
        if (address.isEmpty()) {
            error = "empty Address feild";
            return true;
        }
        return false;
    }

    public boolean isSignUp() {
        return !isEmail() && !isPassword();
    }

    public boolean isMore() {
        return !isEmail() && !isPassword() && !isAddress() && !isPhone() && !isName();
    }

    public String error() {
        return error;
    }
}
