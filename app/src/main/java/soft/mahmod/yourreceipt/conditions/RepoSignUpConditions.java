package soft.mahmod.yourreceipt.conditions;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.regex.Matcher;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.controller.ConnectionInternet;
import soft.mahmod.yourreceipt.model.User;

public class RepoSignUpConditions implements EmailPattern {
    private User user;
    private Application application;
    private MutableLiveData<User> dataCondition;
    private boolean password = false;
    public RepoSignUpConditions(Application application) {
        this.application = application;

    }

    public LiveData<User> signUpConditions(String email, String pass1, String pass2) {
        user = new User();
        dataCondition = new MutableLiveData<>();
        ConnectionInternet connectionInternet = new ConnectionInternet(application);
        if (connectionInternet.isConnection()) {
            confirmPassword(pass1, pass2, email);
            if (password){
                confirmEmail(email);
            }

        } else {
            user.setError(true);
            user.setMessage(getString(R.string.network_conecction));
            dataCondition.setValue(user);
        }

        return dataCondition;
    }



    private void confirmPassword(String pass1, String pass2, String email) {
        if (pass1 != null && pass2 != null) {
            if (pass1.isEmpty() || pass2.isEmpty()) {
                user.setError(true);
                user.setMessage(getString(R.string.empty_password));
            } else {
                if (pass1.length() >= 6) {
                    if (pass1.equals(pass2)) {
                        password = true;
                        user.setError(password);
                        user.setMessage("success");
                        dataCondition.setValue(user);
                    } else {
                        user.setError(true);
                        user.setMessage(getString(R.string.config_password));
                    }
                } else if (pass1.length() > 50) {
                    user.setError(true);
                    user.setMessage(getString(R.string.password_long));
                } else {
                    user.setError(true);
                    user.setMessage(getString(R.string.password_short));
                }
            }
        } else {
            user.setError(true);
            user.setMessage(getString(R.string.success));
        }
        dataCondition.setValue(user);
    }

    private void confirmEmail(String email) {
        if (email != null) {
            if (!email.isEmpty()) {
                if (email.length() > 100) {
                    user.setError(true);
                    user.setMessage(getString(R.string.email_long));
//                    length
                } else {
                    if (validate(email)) {
                        user.setError(false);
                        user.setMessage(getString(R.string.success));
                        //                    success
                    } else {
                        user.setError(true);
                        user.setMessage(getString(R.string.email_unacceptable));
//                        pattern email
                    }
                }
            } else {
                user.setError(true);
                user.setMessage(getString(R.string.email_epty));
//                empty
            }
        } else {
            user.setError(true);
            user.setMessage(getString(R.string.email_epty));
//            null
        }
    }

    private String getString(int rec) {
        return application.getResources().getString(rec);
    }



    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
