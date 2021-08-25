package soft.mahmod.yourreceipt.conditions.catch_registration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import soft.mahmod.yourreceipt.conditions.OnError;
import soft.mahmod.yourreceipt.model.Cash;

public abstract class OnErrorRegistration extends Cash implements OnError {
    protected Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean passwordConfig(String pass1, String pass2) {
        return pass1.trim().equals(pass2.trim());
    }

    @Override
    public boolean lengthInput(String input) {
        return input.length() >= 6 && input.length() < 30;
    }

    @Override
    public boolean isNull(String input) {
        return input != null;
    }

    public boolean validate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    @Override
    public boolean isEmpty(String input) {
        return !input.isEmpty();
    }





}
