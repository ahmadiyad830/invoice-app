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
        dataCondition = new MutableLiveData<>();
        user = new User();
    }

    public LiveData<User> signUpConditions(String email, String pass1, String pass2) {
        ConnectionInternet connectionInternet = new ConnectionInternet(application);
        if (connectionInternet.isConnection()) {
            confirmPassword(pass1, pass2);
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

    public LiveData<User> changePassword(String pass1, String pass2, String pass3) {
        confirmChangePassword(pass1, pass2, pass3);
        return dataCondition;
    }

    private void confirmChangePassword(String pass1, String pass2, String pass3) {
        boolean lengthPassword = pass2.length() >= 6 && pass3.length() >= 6;
        boolean confirm = pass2.equals(pass3) && !pass1.equals(pass3) ;
        if (lengthPassword){
            if (confirm){
                password = false;
                user.setError(password);
                user.setMessage("success");
            }else {
                user.setError(true);
                user.setMessage(getString(R.string.error));
            }
        }else {
            user.setError(true);
            user.setMessage(getString(R.string.password_short));
        }
        dataCondition.setValue(user);
    }


    private void confirmPassword(String pass1, String pass2) {
        if (pass1 != null && pass2 != null) {
            if (pass1.isEmpty() || pass2.isEmpty()) {
                user.setError(true);
                user.setMessage(getString(R.string.empty_password));
            } else {
                if (pass1.length() >= 6) {
                    if (pass1.equals(pass2)) {
                        password = false;
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
