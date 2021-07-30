package soft.mahmod.yourreceipt.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import soft.mahmod.yourreceipt.model.Cash;
import soft.mahmod.yourreceipt.model.Receipt;

public class ResponseReceipt extends Cash {
    @SerializedName("0")
    private List<Receipt> receiptsByEmail;

    public ResponseReceipt(String message, Boolean error, Integer code, List<Receipt> receiptsByEmail) {
        super(message, error, code);
        this.receiptsByEmail = receiptsByEmail;
    }

    public List<Receipt> getReceiptsByEmail() {
        return receiptsByEmail;
    }

    public void setReceiptsByEmail(List<Receipt> receiptsByEmail) {
        this.receiptsByEmail = receiptsByEmail;
    }
}
