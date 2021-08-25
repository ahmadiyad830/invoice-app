package soft.mahmod.yourreceipt.conditions.catch_registration;


public class ConditionsSignIn extends ConditionsSignUp{

    public ConditionsSignIn(String email, String pass1) {
        super(email, pass1);
        isEmpty();
    }
}
