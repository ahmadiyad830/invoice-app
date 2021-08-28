package soft.mahmod.yourreceipt.repository.User;

import android.os.Build;

import androidx.annotation.RequiresApi;

public interface OnEditAccount {

    @RequiresApi(api = Build.VERSION_CODES.P)
    void changePassword( String pass1, String pass2);

    void changePasswordTLow( String pass1, String pass2);

    void uploadImage(String path);

    void changeEmail();

    void forgetPassword(String email);
}
