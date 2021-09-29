package soft.mahmod.yourreceipt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import soft.mahmod.yourreceipt.R;


public class CustomDialogWithBinding<B extends DataBindingUtil> extends Dialog {
    private B b;

    public CustomDialogWithBinding(@NonNull Context context) {
        super(context);
    }

    public void createDialog() {

    }

    public void creatDialogWithBinding(View view, int rec) {
        setContentView(view);
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext()
                , R.drawable.custom_back_icon));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        setCancelable(false);
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
